package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Swarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwarmService {

  private final DockerClient dockerClient;

  public Swarm inspectSwarm() {
    return dockerClient.inspectSwarmCmd().exec();
  }
}
