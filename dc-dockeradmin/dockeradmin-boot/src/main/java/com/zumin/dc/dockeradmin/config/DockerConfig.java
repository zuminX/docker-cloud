package com.zumin.dc.dockeradmin.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Docker配置
 */
@Configuration
public class DockerConfig {

  /**
   * 最大连接数
   */
  private final static int MAX_CONNECTION = 100;

  /**
   * 配置Docker客户端
   *
   * @return 客户端实例
   */
  @Bean
  public DockerClient dockerClient() {
    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
        .dockerHost(config.getDockerHost())
        .maxConnections(MAX_CONNECTION)
        .build();
    return DockerClientImpl.getInstance(config, httpClient);
  }

}
