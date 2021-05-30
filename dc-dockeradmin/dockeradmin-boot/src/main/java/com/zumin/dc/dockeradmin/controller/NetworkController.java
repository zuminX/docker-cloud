package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.Network;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.NetworkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@ComRestController(path = "/network", tags = "Docker网络API接口")
public class NetworkController {

  private final NetworkService networkService;

  @GetMapping("/list")
  public List<Network> listOfAllNetworks() {
    return networkService.listOfAllNetworks();
  }

  @DeleteMapping("/{networkId}")
  public void removeNetwork(@PathVariable String networkId) {
    networkService.removeNetwork(networkId);
  }
}