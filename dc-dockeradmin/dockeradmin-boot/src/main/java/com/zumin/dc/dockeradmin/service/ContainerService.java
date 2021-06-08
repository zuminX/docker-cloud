package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.dockeradmin.enums.ContainerStatus;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 容器的服务层类
 */
@Service
@RequiredArgsConstructor
public class ContainerService {

  private final DockerClient dockerClient;

  /**
   * 列出所有的容器
   *
   * @return 容器列表
   */
  public List<Container> list() {
    return dockerClient.listContainersCmd().withShowAll(true).exec();
  }

  /**
   * 根据容器ID获取容器详情
   *
   * @param id 容器ID
   * @return 容器详情
   */
  public InspectContainerResponse getById(String id) {
    return dockerClient.inspectContainerCmd(id).exec();
  }

  /**
   * 重启指定的容器
   *
   * @param id 容器ID
   */
  public void restart(String id) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(id).exec();
    if (info != null) {
      dockerClient.resizeContainerCmd(id).exec();
    }
  }

  /**
   * 批量重启指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchRestart(List<String> idList) {
    idList.forEach(this::restart);
  }

  /**
   * 停止指定的容器
   *
   * @param id 容器ID
   */
  public void stop(String id) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(id).exec();
    if (info != null) {
      dockerClient.stopContainerCmd(id).exec();
    }
  }

  /**
   * 批量停止指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchStop(List<String> idList) {
    idList.forEach(this::stop);
  }

  /**
   * 启动指定的容器
   *
   * @param id 容器ID
   */
  public void start(String id) {
    dockerClient.startContainerCmd(id).exec();
  }

  /**
   * 批量启动指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchStart(List<String> idList) {
    idList.forEach(this::start);
  }

  /**
   * 暂停指定的容器
   *
   * @param id 容器ID
   */
  public void pause(String id) {
    dockerClient.pauseContainerCmd(id).exec();
  }

  /**
   * 批量暂停指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchPause(List<String> idList) {
    idList.forEach(this::pause);
  }

  /**
   * 取消暂停指定的容器
   *
   * @param id 容器ID
   */
  public void unpause(String id) {
    dockerClient.unpauseContainerCmd(id).exec();
  }

  /**
   * 批量取消暂停指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchUnpause(List<String> idList) {
    idList.forEach(this::unpause);
  }

  /**
   * 杀死指定的容器
   *
   * @param id 容器ID
   */
  public void kill(String id) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(id).exec();
    if (info != null) {
      dockerClient.killContainerCmd(id).exec();
    }
  }

  /**
   * 批量杀死指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchKill(List<String> idList) {
    idList.forEach(this::kill);
  }

  /**
   * 获取指定容器的日志
   *
   * @param id 容器ID
   */
  public void log(String id) {
    //TODO 待完成
  }

  /**
   * 删除指定的容器
   *
   * @param id 容器ID
   */
  public void remove(String id) {
    InspectContainerResponse info = dockerClient.inspectContainerCmd(id).exec();
    if (info != null) {
      dockerClient.removeContainerCmd(id).exec();
    }
  }

  /**
   * 批量删除指定的容器
   *
   * @param idList 容器ID列表
   */
  public void batchRemove(List<String> idList) {
    idList.forEach(this::remove);
  }

  /**
   * 对指定容器进行重命名
   *
   * @param id      容器ID
   * @param newName 新的名称
   */
  public void rename(String id, String newName) {
    dockerClient.renameContainerCmd(id).withName(newName).exec();
  }

  /**
   * 获取容器的统计信息
   *
   * @return 容器的统计信息
   */
  public ContainerStatsVO getStatistics() {
    List<String> containerStatusList = ConvertUtils.convert(list(), Container::getState);
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
