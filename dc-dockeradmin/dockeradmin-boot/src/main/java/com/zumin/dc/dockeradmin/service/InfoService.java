package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker信息的服务层类
 */
@Service
@RequiredArgsConstructor
public class InfoService {

  private final DockerClient dockerClient;

  /**
   * 获取Docker信息
   *
   * @return 信息
   */
  public Info get() {
    return dockerClient.infoCmd().exec();
  }
}
