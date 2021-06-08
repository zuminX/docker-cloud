package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ContainerConvert;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerVO;
import com.zumin.dc.dockeradmin.service.ContainerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/container", tags = "Docker容器API接口")
public class ContainerController {

  private final ContainerService containerService;
  private final ContainerConvert containerConvert;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的容器信息")
  public List<ContainerVO> listContainer() {
    return ConvertUtils.convert(containerService.list(), containerConvert::convert);
  }

  @GetMapping("/info")
  @ApiOperation("获取指定容器的详细信息")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public InspectContainerResponse getContainerInfo(@RequestParam("containerId") String containerId) {
    return containerService.getById(containerId);
  }

  @PostMapping("/batch/restart")
  @ApiOperation("重启所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void restartContainers(@RequestBody List<String> containerIdList) {
    containerService.batchRestart(containerIdList);
  }

  @GetMapping("/restart")
  @ApiOperation("重启指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void restartContainer(@RequestParam("containerId") String containerId) {
    containerService.restart(containerId);
  }

  @PostMapping("/batch/pause")
  @ApiOperation("暂停所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void pauseContainers(@RequestBody List<String> containerIdList) {
    containerService.batchPause(containerIdList);
  }

  @GetMapping("/pause")
  @ApiOperation("暂停指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void pauseContainer(@RequestParam("containerId") String containerId) {
    containerService.pause(containerId);
  }

  @PostMapping("/batch/unpause")
  @ApiOperation("取消暂停所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void unpauseContainers(@RequestBody List<String> containerIdList) {
    containerService.batchUnpause(containerIdList);
  }

  @GetMapping("/unpause")
  @ApiOperation("取消暂停指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void unpauseContainer(@RequestParam("containerId") String containerId) {
    containerService.unpause(containerId);
  }

  @PostMapping("/batch/stop")
  @ApiOperation("停止所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void stopContainers(@RequestBody List<String> containerIdList) {
    containerService.batchStop(containerIdList);
  }

  @GetMapping("/stop")
  @ApiOperation("停止指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void stopContainer(@RequestParam("containerId") String containerId) {
    containerService.stop(containerId);
  }

  @PostMapping("/batch/start")
  @ApiOperation("启动所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void startContainers(@RequestBody List<String> containerIdList) {
    containerService.batchStart(containerIdList);
  }

  @GetMapping("/start")
  @ApiOperation("启动指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void startContainer(@RequestParam("containerId") String containerId) {
    containerService.start(containerId);
  }

  @PostMapping("/batch/kill")
  @ApiOperation("杀死所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void killContainers(@RequestBody List<String> containerIdList) {
    containerService.batchKill(containerIdList);
  }

  @GetMapping("/kill")
  @ApiOperation("杀死指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void killContainer(@RequestParam("containerId") String containerId) {
    containerService.kill(containerId);
  }

  @GetMapping("/log")
  @ApiOperation("获取指定的容器的日志信息")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void logContainer(@RequestParam("containerId") String containerId) {
    containerService.log(containerId);
  }

  @PostMapping("/batch/remove")
  @ApiOperation("删除所有指定的容器")
  @ApiImplicitParam(name = "containerIdList", value = "容器ID列表", required = true, dataTypeClass = List.class)
  public void removeContainers(@RequestBody List<String> containerIdList) {
    containerService.batchRemove(containerIdList);
  }

  @GetMapping("/remove")
  @ApiOperation("删除指定的容器")
  @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class)
  public void removeContainer(@RequestParam("containerId") String containerId) {
    containerService.remove(containerId);
  }

  @PutMapping("/rename")
  @ApiOperation("重命名指定的容器")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "containerId", value = "容器ID", required = true, dataTypeClass = String.class),
      @ApiImplicitParam(name = "name", value = "新的名称", required = true, dataTypeClass = String.class)
  })
  public void renameContainer(@RequestParam("containerId") String containerId, @RequestParam("name") String name) {
    containerService.rename(containerId, name);
  }
}