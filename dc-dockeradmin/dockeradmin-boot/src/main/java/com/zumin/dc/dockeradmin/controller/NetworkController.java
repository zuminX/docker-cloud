package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.Network;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.NetworkService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/network", tags = "Docker网络API接口")
public class NetworkController {

  private final NetworkService networkService;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的网络信息")
  public List<Network> listNetwork() {
    return networkService.list();
  }

  @GetMapping("/remove")
  @ApiOperation("删除指定的网络")
  @ApiImplicitParam(name = "networkId", value = "网络ID", required = true, dataTypeClass = String.class)
  public void removeNetwork(@RequestParam("networkId") String networkId) {
    networkService.remove(networkId);
  }
}