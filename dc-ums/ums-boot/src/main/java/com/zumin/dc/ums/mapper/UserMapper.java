package com.zumin.dc.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.ums.pojo.body.ModifyUserBody;
import com.zumin.dc.ums.pojo.body.SearchUserBody;
import com.zumin.dc.ums.pojo.entity.UserEntity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

  UserEntity selectWithRoleById(@Param("userId") Long userId);

  /**
   * 统计在[startCreateTime,endCreateTime)天中创建的用户数
   *
   * @param startDate 起始创建时间
   * @param endDate   终止创建时间
   * @return 用户数
   */
  Integer countNewUserByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startDate 起始登录时间
   * @param endDate   终止登录时间
   * @return 用户数
   */
  Integer countRecentLoginUserByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  UserEntity selectWithRoleByUsername(@Param("username") String username);

  /**
   * 查找系统所有用户，且用户带有角色信息
   *
   * @return 用户列表
   */
  List<UserEntity> selectAllWithRole();

  /**
   * 查找系统符合条件的用户，且用户带有角色信息
   *
   * @return 用户列表
   */
  List<UserEntity> selectByConditionWithRole(@Param("user") SearchUserBody user);

  /**
   * 查找系统中用户名或昵称中包含指定名称的用户给，且用户带有角色信息
   *
   * @param name 名称
   * @return 用户列表
   */
  List<UserEntity> selectByNameWithRole(@Param("name") String name);

  /**
   * 根据ID更新用户
   *
   * @param user 用户修改对象
   * @return 更新的行数
   */
  int updateModifyById(@Param("user") ModifyUserBody user);

  List<String> selectNicknameByIdIn(@Param("idCollection") Collection<Long> idCollection);
}