package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Config;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 配置的服务层类
 */
@Service
@RequiredArgsConstructor
public class ConfigService {

  private final DockerClient dockerClient;

  /**
   * 列出所有的配置
   *
   * @return 配置列表
   */
  public List<Config> list() {
    return dockerClient.listConfigsCmd().exec();
  }

  /**
   * 删除指定的配置
   *
   * @param id 配置ID
   */
  public void delete(String id) {
    dockerClient.removeConfigCmd(id).exec();
  }
}