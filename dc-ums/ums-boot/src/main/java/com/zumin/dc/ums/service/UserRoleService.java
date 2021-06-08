package com.zumin.dc.ums.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.ums.mapper.UserRoleMapper;
import com.zumin.dc.ums.pojo.entity.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户角色的业务层类
 */
@Service
@RequiredArgsConstructor
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRoleEntity> {

}
