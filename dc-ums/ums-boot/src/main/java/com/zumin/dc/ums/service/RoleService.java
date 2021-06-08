package com.zumin.dc.ums.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.ums.mapper.RoleMapper;
import com.zumin.dc.ums.pojo.entity.RoleEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色的业务层类
 */
@Service
@RequiredArgsConstructor
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> {

  public List<String> listNameByUserId(Long id) {
    return baseMapper.selectNameByUserId(id);
  }
}
