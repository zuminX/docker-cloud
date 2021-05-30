package com.zumin.dc.ums.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.auth.feign.OAuthFeign;
import com.zumin.dc.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.core.auth.AuthGrantType;
import com.zumin.dc.common.core.auth.AuthParamName;
import com.zumin.dc.common.core.constant.PermissionConstants;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.mybatis.page.PageParam;
import com.zumin.dc.common.mybatis.utils.PageUtils;
import com.zumin.dc.common.web.log.BusinessType;
import com.zumin.dc.common.web.log.Log;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.convert.UserConvert;
import com.zumin.dc.ums.enums.UmsStatusCode;
import com.zumin.dc.ums.exception.UserException;
import com.zumin.dc.ums.mapper.UserMapper;
import com.zumin.dc.ums.pojo.body.AddUserBody;
import com.zumin.dc.ums.pojo.body.LoginBody;
import com.zumin.dc.ums.pojo.body.ModifyUserBody;
import com.zumin.dc.ums.pojo.body.RegisterUserBody;
import com.zumin.dc.ums.pojo.body.SearchUserBody;
import com.zumin.dc.ums.pojo.entity.RoleEntity;
import com.zumin.dc.ums.pojo.entity.UserEntity;
import com.zumin.dc.ums.pojo.entity.UserRoleEntity;
import com.zumin.dc.ums.pojo.vo.UserDetailVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, UserEntity> {

  private final OAuthFeign oAuthFeign;

  private final RoleService roleService;

  private final UserRoleService userRoleService;

  private final UserConvert userConvert;

  @Value("${auth.client-secret}")
  private String clientSecret;

  /**
   * 根据用户名查询对应的系统用户，并带上该用户对应的角色
   *
   * @param username 用户名
   * @return 系统用户
   */
  public UserEntity getUserWithRoleByUsername(String username) {
    return baseMapper.selectWithRoleByUsername(username);
  }

  /**
   * 获取系统用户列表
   *
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> getUserList() {
    return PageUtils.getPage(baseMapper::selectAllWithRole, userConvert::entityToDetailVO);
  }

  /**
   * 修改用户信息
   *
   * @param modifyUserBody 修改的用户信息
   */
  @Transactional
  @Log(value = "修改用户", businessType = BusinessType.UPDATE)
  public void modifyUser(ModifyUserBody modifyUserBody) {
    checkRoleName(modifyUserBody);
    checkReUsername(modifyUserBody.getUsername(), modifyUserBody.getId());
    baseMapper.updateModifyById(modifyUserBody);
    updateRoleIdByUserId(modifyUserBody.getId(), modifyUserBody.getRoleNameList());
  }

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  @Transactional
  @Log(value = "新增用户", isSaveParameterData = false)
  public void addUser(AddUserBody addUserBody) {
    checkUsername(addUserBody.getUsername());
    UserEntity user = userConvert.addBodyToEntity(addUserBody);
    baseMapper.insert(user);
    insertUserRole(user.getId(), addUserBody.getRoleNameList());
  }

  /**
   * 根据条件搜索用户
   *
   * @param searchUserBody 搜索用户的条件
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUser(SearchUserBody searchUserBody) {
    searchUserBody.setUsername(StrUtil.trim(searchUserBody.getUsername()));
    searchUserBody.setNickname(StrUtil.trim(searchUserBody.getNickname()));
    return PageUtils.getPage(() -> baseMapper.selectByConditionWithRole(searchUserBody), userConvert::entityToDetailVO);
  }

  /**
   * 根据用户名或昵称搜索用户
   *
   * @param name 名称
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUserByName(String name) {
    return PageUtils.getPage(() -> baseMapper.selectByNameWithRole(name), userConvert::entityToDetailVO);
  }

  /**
   * 更新用户的角色
   *
   * @param userId       用户ID
   * @param roleNameList 角色名列表
   */
  private void updateRoleIdByUserId(Long userId, List<String> roleNameList) {
    List<UserRoleEntity> userRoleList = roleService.list(Wrappers.lambdaQuery(RoleEntity.class).select(RoleEntity::getId).in(RoleEntity::getName, roleNameList))
        .stream()
        .map(role -> new UserRoleEntity(userId, role.getId()))
        .collect(Collectors.toList());
    userRoleService.remove(Wrappers.lambdaQuery(UserRoleEntity.class).eq(UserRoleEntity::getUserId, userId));
    userRoleService.saveBatch(userRoleList);
  }

  /**
   * 检查待修改的用户的角色名列表是否为空或管理员。若是，则抛出用户异常
   *
   * @param modifyUserBody 修改的用户对象
   */
  private void checkRoleName(ModifyUserBody modifyUserBody) {
    List<String> roleNameList = roleService.listNameByUserId(modifyUserBody.getId());
    if (CollUtil.isEmpty(roleNameList)) {
      throw new UserException(UmsStatusCode.USER_NOT_FOUND);
    }
    if (SecurityUtils.hasAdmin(roleNameList)) {
      throw new UserException(UmsStatusCode.USER_NOT_MODIFY_AUTHORITY);
    }
  }

  /**
   * 检查重新设置的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   * @param userId   用户ID
   */
  private void checkReUsername(String username, Long userId) {
    UserEntity user = getUserByUsername(username);
    if (user != null && !user.getId().equals(userId)) {
      throw new UserException(UmsStatusCode.USER_HAS_EQUAL_NAME);
    }
  }

  /**
   * 根据用户名查找对应的用户
   *
   * @param username 用户名
   * @return 用户
   */
  private UserEntity getUserByUsername(String username) {
    return baseMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getUsername, username));
  }

  /**
   * 插入用户角色
   *
   * @param userId       用户ID
   * @param roleNameList 角色名列表
   */
  private void insertUserRole(Long userId, List<String> roleNameList) {
    List<RoleEntity> roleList = roleService.list(Wrappers.lambdaQuery(RoleEntity.class).in(RoleEntity::getName, roleNameList));
    List<UserRoleEntity> userRoles = roleList.stream()
        .map(role -> new UserRoleEntity(userId, role.getId()))
        .collect(Collectors.toList());
    userRoleService.saveBatch(userRoles);
  }

  /**
   * 发送登录请求到统一认证服务中
   *
   * @param loginBody 登录信息
   * @return 登录的Token信息
   */
  public CommonResult<OAuth2TokenDTO> login(LoginBody loginBody) {
    Map<String, String> params = new HashMap<>();
    params.put(AuthParamName.CLIENT_ID, AuthConstants.USER_CLIENT_ID);
    params.put(AuthParamName.CLIENT_SECRET, clientSecret);
    params.put(AuthParamName.GRANT_TYPE, AuthGrantType.PASSWORD);
    params.put(AuthParamName.USERNAME, loginBody.getUsername());
    params.put(AuthParamName.PASSWORD, loginBody.getPassword());
    params.put(AuthParamName.CAPTCHA_UUID, loginBody.getUuid());
    params.put(AuthParamName.CAPTCHA_CODE, loginBody.getCode());
    return oAuthFeign.postAccessToken(params);
  }

  /**
   * 根据用户ID查询对应的系统用户，并带上该用户对应的角色
   *
   * @param userId 用户ID
   * @return 系统用户
   */
  public UserEntity getUserWithRoleById(Long userId) {
    return baseMapper.selectWithRoleById(userId);
  }

  /**
   * 注册用户
   *
   * @param registerUserBody 注册用户对象
   * @return 用户显示层对象
   */
  @Transactional
  @Log(value = "注册用户", isSaveParameterData = false)
  public UserEntity registerUser(RegisterUserBody registerUserBody) {
    checkUsername(registerUserBody.getUsername().trim());
    UserEntity user = convertToUser(registerUserBody);
    baseMapper.insert(user);
    List<RoleEntity> roleList = insertUserRole(user.getId());
    user.setRoleList(roleList);
    return user;
  }

  /**
   * 插入用户角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  private List<RoleEntity> insertUserRole(Long userId) {
    List<RoleEntity> roleList = roleService.list(Wrappers.lambdaQuery(RoleEntity.class).in(RoleEntity::getName, PermissionConstants.USER_ROLE_NAME));
    List<UserRoleEntity> userRoles = roleList.stream()
        .map(role -> new UserRoleEntity(userId, role.getId()))
        .collect(Collectors.toList());
    userRoleService.saveBatch(userRoles);
    return roleList;
  }

  /**
   * 将注册用户信息对象转换为用户表对应的对象 对密码进行加密
   *
   * @param registerUserBody 注册用户信息对象
   * @return 用户表对应的对象
   */
  private UserEntity convertToUser(RegisterUserBody registerUserBody) {
    return UserEntity.builder()
        .username(registerUserBody.getUsername().trim())
        .password(SecurityUtils.encodePassword(registerUserBody.getPassword()))
        .nickname(registerUserBody.getNickname())
        .enabled(1).build();
  }

  /**
   * 检查指定的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   */
  private void checkUsername(String username) {
    if (baseMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getUsername, username)) != null) {
      throw new UserException(UmsStatusCode.USER_HAS_EQUAL_NAME);
    }
  }

  /**
   * 更新头像地址
   *
   * @param avatarPath 头像地址
   * @param userId 用户ID
   */
  public void updateAvatar(String avatarPath, Long userId) {
    update(Wrappers.lambdaUpdate(UserEntity.class).set(UserEntity::getAvatar, avatarPath).eq(UserEntity::getId, userId));
  }
}
