package com.zumin.dc.dockerserve.controller.admin;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.service.ServeLinkService;
import com.zumin.dc.dockerserve.service.ServeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ComRestController(path = "/admin/serve", tags = "应用服务API接口")
public class ServeAdminController extends BaseController {

  private final ServeService serveService;
  private final ServeLinkService serveLinkService;

}
