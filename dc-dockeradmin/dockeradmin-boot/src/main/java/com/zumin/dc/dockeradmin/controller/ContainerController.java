package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ContainerConvert;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerVO;
import com.zumin.dc.dockeradmin.service.ContainerService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public List<ContainerVO> listOfAllContainers() {
    return ConvertUtils.convert(containerService.listOfAllContainers(), containerConvert::convert);
  }

  @GetMapping("/{containerId}")
  public InspectContainerResponse getContainerInfo(@PathVariable("containerId") String containerId) {
    return containerService.getContainerInfo(containerId);
  }

  @PostMapping("/restart")
  public void restartContainers(@RequestBody ArrayList<String> containers) {
    containerService.restartContainers(containers);
  }

  @GetMapping("/restart/{containerId}")
  public void restartContainer(@PathVariable("containerId") String containerId) {
    containerService.restartContainer(containerId);
  }

  @PostMapping("/pause")
  public void pauseContainers(@RequestBody ArrayList<String> containers) {
    containerService.pauseContainers(containers);
  }

  @GetMapping("/pause/{containerId}")
  public void pauseContainer(@PathVariable("containerId") String containerId) {
    containerService.pauseContainer(containerId);
  }

  @PostMapping("/unpause")
  public void unpauseContainers(@RequestBody ArrayList<String> containers) {
    containerService.unpauseContainers(containers);
  }

  @GetMapping("/unpause/{containerId}")
  public void unpauseContainer(@PathVariable("containerId") String containerId) {
    containerService.unpauseContainer(containerId);
  }

  @PostMapping("/stop")
  public void stopContainers(@RequestBody ArrayList<String> containers) {
    containerService.stopContainers(containers);
  }

  @GetMapping("/stop/{containerId}")
  public void stopContainer(@PathVariable("containerId") String containerId) {
    containerService.stopContainer(containerId);
  }

  @PostMapping("/start")
  public void startContainers(@RequestBody ArrayList<String> containers) {
    containerService.startContainers(containers);
  }

  @GetMapping("/start/{containerId}")
  public void startContainer(@PathVariable("containerId") String containerId) {
    containerService.startContainer(containerId);
  }

  @PostMapping("/kill")
  public void killContainers(@RequestBody ArrayList<String> containers) {
    containerService.killContainers(containers);
  }

  @GetMapping("/kill/{containerId}")
  public void killContainer(@PathVariable("containerId") String containerId) {
    containerService.killContainer(containerId);
  }

  @GetMapping("/log/{containerId}")
  public void logContainer(@PathVariable("containerId") String containerId) {
    containerService.logContainer(containerId);
  }

  @PostMapping("/remove")
  public void removeContainers(@RequestBody ArrayList<String> containers) {
    containerService.removeContainers(containers);
  }

  @GetMapping("/remove/{containerId}")
  public void removeContainer(@PathVariable("containerId") String containerId) {
    containerService.removeContainer(containerId);
  }

  @PutMapping("/rename/{containerId}")
  public void renameContainer(@PathVariable String containerId, @RequestParam("name") String name) {
    containerService.renameContainer(containerId, name);
  }
}