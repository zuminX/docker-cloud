package com.zumin.dc.dockerserve.controller.user;

import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ServeConvert;
import com.zumin.dc.dockerserve.convert.PortConvert;
import com.zumin.dc.dockerserve.pojo.bo.ComposeServeInfo;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationServeInfo;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ContainerService;
import com.zumin.dc.dockerserve.validator.CheckApplicationAccess;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/serve", tags = "应用服务API接口")
public class ServeController extends BaseController {

  private final ApplicationService applicationService;
  private final ContainerService containerService;
  private final ServeService serveService;
  private final ServeConvert serveConvert;
  private final PortConvert portConvert;

  @GetMapping("/info")
  public List<ApplicationServeInfo> info(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    List<ComposeServeInfo> composeServeList = applicationService.getComposeServeList(application.getId());
    List<Container> containerList = containerService.list(ConvertUtils.convert(composeServeList, ComposeServeInfo::getContainerId));
    List<ServeEntity> serveEntityList = serveService.listByApplicationId(application.getId());
    List<ApplicationServeInfo> result = new ArrayList<>();
    for (ServeEntity serveEntity : serveEntityList) {
      ApplicationServeInfo.builder().name(serveEntity.getName()).description(serveEntity.get)
    }
    return result;
  }

  @GetMapping("/search")
  public List<ServeNameVO> searchByName(@RequestParam(value = "name") String name) {
    return ConvertUtils.convert(serveService.listShareOrUserIdByName(SecurityUtils.getUserId(), name), serveConvert::convert);
  }
}
