package com.zumin.dc.ums.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.ums.mapper.ResourceRoleMapper;
import com.zumin.dc.ums.pojo.entity.ResourceRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceRoleService extends ServiceImpl<ResourceRoleMapper, ResourceRoleEntity> {

}
