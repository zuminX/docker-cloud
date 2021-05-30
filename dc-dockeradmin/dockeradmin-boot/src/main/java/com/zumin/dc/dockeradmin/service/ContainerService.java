package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.dockeradmin.enums.ContainerStatus;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerStatsVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerService {

  private final DockerClient dockerClient;

  public List<Container> listOfAllContainers() {
    return dockerClient.listContainersCmd().withShowAll(true).exec();
  }

  public InspectContainerResponse getContainerInfo(String containerId) {
    return dockerClient.inspectContainerCmd(containerId).exec();
  }

  public void restartContainer(String containerId) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(containerId).exec();
    if (info != null) {
      dockerClient.resizeContainerCmd(containerId).exec();
    }
  }

  public void restartContainers(ArrayList<String> containers) {
    containers.forEach(this::restartContainer);
  }

  public void stopContainer(String containerId) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(containerId).exec();
    if (info != null) {
      dockerClient.stopContainerCmd(containerId).exec();
    }
  }

  public void stopContainers(ArrayList<String> containers) {
    containers.forEach(this::stopContainer);
  }

  public void startContainer(String containerId) {
    dockerClient.startContainerCmd(containerId).exec();
  }

  public void startContainers(ArrayList<String> containers) {
    containers.forEach(this::startContainer);
  }

  public void pauseContainer(String containerId) {
    dockerClient.pauseContainerCmd(containerId).exec();
  }

  public void pauseContainers(ArrayList<String> containers) {
    containers.forEach(this::pauseContainer);
  }

  public void unpauseContainer(String containerId) {
    dockerClient.unpauseContainerCmd(containerId).exec();
  }

  public void unpauseContainers(ArrayList<String> containers) {
    containers.forEach(this::unpauseContainer);
  }

  public void killContainer(String containerId) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(containerId).exec();
    if (info != null) {
      dockerClient.killContainerCmd(containerId).exec();
    }
  }

  public void killContainers(ArrayList<String> containers) {
    containers.forEach(this::killContainer);
  }

  public void logContainer(String containerId) {
    //TODO 待完成
  }

  public void removeContainer(String containerId) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(containerId).exec();
    if (info != null) {
      dockerClient.removeContainerCmd(containerId).exec();
    }
  }

  public void removeContainers(ArrayList<String> containers) {
    containers.forEach(this::removeContainer);
  }

  public void renameContainer(String containerId, String newName) {
    dockerClient.renameContainerCmd(containerId).withName(newName).exec();
  }

  public ContainerStatsVO getStatistics() {
    List<String> containerStatusList = ConvertUtils.convert(listOfAllContainers(), Container::getState);
    long createTotal = containerStatusList.stream().filter(status -> status.equals(ContainerStatus.CREATED.getName())).count();
    long runningTotal = containerStatusList.stream().filter(status -> status.equals(ContainerStatus.RUNNING.getName())).count();
    long pausedTotal = containerStatusList.stream().filter(status -> status.equals(ContainerStatus.PAUSED.getName())).count();
    long restartingTotal = containerStatusList.stream().filter(status -> status.equals(ContainerStatus.RESTARTING.getName())).count();
    long exitedTotal = containerStatusList.stream().filter(status -> status.equals(ContainerStatus.EXITED.getName())).count();
    return ContainerStatsVO.builder()
        .total(containerStatusList.size())
        .createdTotal(createTotal)
        .runningTotal(runningTotal)
        .pausedTotal(pausedTotal)
        .restartingTotal(restartingTotal)
        .exitedTotal(exitedTotal)
        .build();
  }
}
