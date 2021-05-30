package com.zumin.dc.dockerserve.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerService {

  private final DockerClient dockerClient;

  public List<Container> list(List<String> containerIdList) {
    return dockerClient.listContainersCmd().withIdFilter(containerIdList).exec();
  }
}
