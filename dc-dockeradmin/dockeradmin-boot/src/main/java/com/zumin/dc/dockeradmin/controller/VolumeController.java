package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.VolumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@ComRestController(path = "/volume", tags = "Docker卷API接口")
public class VolumeController {

  private final VolumeService volumeService;

  @GetMapping("/list")
  public List<InspectVolumeResponse> listOfAllVolumes() {
    return volumeService.listOfAllVolumes();
  }

  @DeleteMapping("/{volumeName}")
  public void removeNetwork(@PathVariable String volumeName) {
    volumeService.removeVolume(volumeName);
  }
}