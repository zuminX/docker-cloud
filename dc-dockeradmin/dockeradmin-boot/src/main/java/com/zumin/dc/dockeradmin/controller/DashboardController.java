package com.zumin.dc.dockeradmin.controller;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.InfoConvert;
import com.zumin.dc.dockeradmin.convert.VersionConvert;
import com.zumin.dc.dockeradmin.pojo.vo.DashBoardStatsVO;
import com.zumin.dc.dockeradmin.service.ContainerService;
import com.zumin.dc.dockeradmin.service.ImageService;
import com.zumin.dc.dockeradmin.service.InfoService;
import com.zumin.dc.dockeradmin.service.NetworkService;
import com.zumin.dc.dockeradmin.service.VersionService;
import com.zumin.dc.dockeradmin.service.VolumeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/dashboard", tags = "Docker仪表板API接口")
public class DashboardController {

  private final ContainerService containerService;
  private final ImageService imageService;
  private final NetworkService networkService;
  private final VolumeService volumeService;
  private final InfoService infoService;
  private final VersionService versionService;

  private final InfoConvert infoConvert;
  private final VersionConvert versionConvert;

  @GetMapping("/stats")
  @ApiOperation("获取Docker统计信息")
  public DashBoardStatsVO stats() {
    return DashBoardStatsVO.builder()
        .containerStats(containerService.getStatistics())
        .imageStats(imageService.getStatistics())
        .networkStats(networkService.getStatistics())
        .volumeStats(volumeService.getStatistics())
        .info(infoConvert.convert(infoService.get()))
        .version(versionConvert.convert(versionService.get()))
        .build();
  }
}
