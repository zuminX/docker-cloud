package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Swarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker集群的服务层类
 */
@Service
@RequiredArgsConstructor
public class SwarmService {

  private final DockerClient dockerClient;

  /**
   * 获取集群信息
   *
   * @return 集群对象
   */
  public Swarm get() {
    return dockerClient.inspectSwarmCmd().exec();
  }
}
