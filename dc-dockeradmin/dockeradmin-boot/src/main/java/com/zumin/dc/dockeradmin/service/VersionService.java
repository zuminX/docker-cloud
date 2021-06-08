package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker版本的服务层类
 */
@Service
@RequiredArgsConstructor
public class VersionService {

  private final DockerClient dockerClient;

  /**
   * 获取版本信息
   *
   * @return 版本对象
   */
  public Version get() {
    return dockerClient.versionCmd().exec();
  }
}
