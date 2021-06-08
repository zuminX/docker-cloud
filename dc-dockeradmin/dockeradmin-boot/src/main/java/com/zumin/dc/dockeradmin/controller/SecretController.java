package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.Secret;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.SecretService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/secret", tags = "Docker机密API接口")
public class SecretController {

  private final SecretService secretService;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的秘钥信息")
  public List<Secret> listSecret() {
    return secretService.list();
  }

  @GetMapping("/remove")
  @ApiOperation("删除指定的秘钥")
  @ApiImplicitParam(name = "secretId", value = "秘钥ID", required = true, dataTypeClass = String.class)
  public void removeSecret(@RequestParam("secretId") String secretId) {
    secretService.remove(secretId);
  }
}