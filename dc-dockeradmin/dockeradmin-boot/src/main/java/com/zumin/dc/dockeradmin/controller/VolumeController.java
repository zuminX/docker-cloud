package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.VolumeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/volume", tags = "Docker卷API接口")
public class VolumeController {

  private final VolumeService volumeService;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的卷信息")
  public List<InspectVolumeResponse> listVolume() {
    return volumeService.list();
  }

  @GetMapping("/remove")
  @ApiOperation("删除指定的卷")
  @ApiImplicitParam(name = "volumeName", value = "卷的名称", required = true, dataTypeClass = String.class)
  public void removeVolume(@RequestParam("volumeName") String volumeName) {
    volumeService.remove(volumeName);
  }
}