package com.zumin.dc.dockerserve.controller.user;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.pojo.vo.UserStatisticsVO;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.service.ServeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/dashboard", tags = "用户仪表板API接口")
public class DashboardController {

  private final ApplicationService applicationService;
  private final ImageService imageService;
  private final ServeService serveService;

  @GetMapping("/statistics")
  @ApiOperation("获取当前用户的统计信息")
  public UserStatisticsVO statistics() {
    Long userId = SecurityUtils.getUserId();
    return UserStatisticsVO.builder()
        .applicationTotal(applicationService.countByUserId(userId))
        .imageTotal(imageService.countByUserId(userId))
        .serveTotal(serveService.countByUserId(userId))
        .build();
  }
}
