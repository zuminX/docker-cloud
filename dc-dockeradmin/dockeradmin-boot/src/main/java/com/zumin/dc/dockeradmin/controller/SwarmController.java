package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.Swarm;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.SwarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/swarm", tags = "Docker集群API接口")
public class SwarmController {

  private final SwarmService swarmService;

  @GetMapping("/info")
  public Swarm inspectSwarm() {
    return swarmService.inspectSwarm();
  }
}