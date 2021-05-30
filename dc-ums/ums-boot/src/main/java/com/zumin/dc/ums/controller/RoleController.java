package com.zumin.dc.ums.controller;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.ums.pojo.entity.RoleEntity;
import com.zumin.dc.ums.service.RoleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/role", tags = "系统角色API接口")
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/roleList")
  @ApiOperation("获取系统角色列表")
  public List<RoleEntity> getRoleList() {
    return roleService.list();
  }
}
