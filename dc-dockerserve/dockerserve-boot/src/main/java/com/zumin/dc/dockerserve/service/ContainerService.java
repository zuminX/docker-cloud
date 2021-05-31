package com.zumin.dc.dockerserve.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 容器服务类
 */
@Service
@RequiredArgsConstructor
public class ContainerService {

  private final DockerClient dockerClient;

  /**
   * 列出Docker所有指定ID的容器
   *
   * @param containerIdList 容器ID列表
   * @return 容器列表
   */
  public List<Container> list(List<String> containerIdList) {
    return dockerClient.listContainersCmd().withIdFilter(containerIdList).exec();
  }

  /**
   * 根据容器ID获取Docker容器
   *
   * @param containerId 容器ID
   * @return 容器对象
   */
  public Container getById(String containerId) {
    List<Container> list = list(Collections.singletonList(containerId));
    return list.isEmpty() ? null : list.get(0);
  }
}
