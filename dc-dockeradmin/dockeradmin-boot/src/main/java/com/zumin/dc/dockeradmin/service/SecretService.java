package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Secret;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker秘钥的服务层类
 */
@Service
@RequiredArgsConstructor
public class SecretService {

  private final DockerClient dockerClient;

  /**
   * 列出所有的秘钥
   *
   * @return 秘钥列表
   */
  public List<Secret> list() {
    return dockerClient.listSecretsCmd().exec();
  }

  /**
   * 删除指定的秘钥
   *
   * @param id 秘钥ID
   */
  public void remove(String id) {
    dockerClient.removeSecretCmd(id).exec();
  }
}