package com.zumin.dc.dockerserve.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerPort;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
   * 根据名称获取容器对象
   *
   * @param containerName 容器名称
   * @return 容器
   */
  public Container getByName(String containerName) {
    List<Container> list = dockerClient.listContainersCmd().withNameFilter(Collections.singleton(containerName)).exec();
    return list.isEmpty() ? null : list.get(0);
  }

  /**
   * 获取容器私有端口到外部端口的映射
   *
   * @param container 容器
   * @return 私有端口->外部端口的映射表
   */
  public Map<Integer, Integer> getPortMap(Container container) {
    ContainerPort[] ports = container.getPorts();
    return Arrays.stream(ports)
        .filter(port -> port.getPrivatePort() != null && port.getPublicPort() != null)
        .collect(Collectors.toMap(ContainerPort::getPrivatePort, ContainerPort::getPublicPort, (a, b) -> b));
  }
}
