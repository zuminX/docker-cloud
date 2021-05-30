package com.zumin.dc.ums.convert;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.dto.UserDTO;
import com.zumin.dc.ums.pojo.body.AddUserBody;
import com.zumin.dc.ums.pojo.entity.RoleEntity;
import com.zumin.dc.ums.pojo.entity.UserEntity;
import com.zumin.dc.ums.pojo.vo.UserDetailVO;
import com.zumin.dc.ums.pojo.vo.UserVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = SecurityUtils.class)
public interface UserConvert {

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  UserVO convert(UserEntity user);

  /**
   * 从实体转换为传输层
   *
   * @param user 系统用户
   * @return 传输层对象
   */
  @Mapping(target = "roleIds", source = "roleList")
  @Mapping(target = "clientId", ignore = true)
  UserDTO entityToDTO(UserEntity user);

  /**
   * 将用户表对应的对象转换为用户详情显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户详情显示层对象
   */
  UserDetailVO entityToDetailVO(UserEntity user);

  /**
   * 将新增用户对象转化为用户表对应的对象
   *
   * @param addUserBody 新增用户对象
   * @return 用户表对应的对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "avatar", ignore = true)
  @Mapping(target = "roleList", ignore = true)
  @Mapping(target = "password", expression = "java(SecurityUtils.encodePassword(addUserBody.getPassword()))")
  UserEntity addBodyToEntity(AddUserBody addUserBody);

  /**
   * 将角色列表转换为角色ID列表
   *
   * @param roleList 角色列表
   * @return 角色ID列表
   */
  default List<Long> roleListToRoleIdList(List<RoleEntity> roleList) {
    return ConvertUtils.convert(roleList, RoleEntity::getId);
  }
}
