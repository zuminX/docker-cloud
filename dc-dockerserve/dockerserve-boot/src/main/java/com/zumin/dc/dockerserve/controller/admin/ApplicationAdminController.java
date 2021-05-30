package com.zumin.dc.dockerserve.controller.admin;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.service.ApplicationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@ComRestController(path = "/admin/application", tags = "应用API接口")
public class ApplicationAdminController extends BaseController {

  private final ApplicationService applicationService;

  @PostMapping("/save")
  public void save(@RequestBody ApplicationEntity entity) {
    entity.setUpdateTime(LocalDateTime.now());
    applicationService.save(entity);
  }
}
