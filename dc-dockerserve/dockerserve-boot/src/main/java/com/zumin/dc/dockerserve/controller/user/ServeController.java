package com.zumin.dc.dockerserve.controller.user;

import cn.hutool.core.util.StrUtil;
import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ServeConvert;
import com.zumin.dc.dockerserve.enums.ContainerState;
import com.zumin.dc.dockerserve.pojo.bo.ComposeServeInfo;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationServeInfo;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationServeInfo.ApplicationServeInfoBuilder;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ContainerService;
import com.zumin.dc.dockerserve.service.ServeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/serve", tags = "服务API接口")
public class ServeController extends BaseController {

  private final ApplicationService applicationService;
  private final ContainerService containerService;
  private final ServeService serveService;
  private final ServeConvert serveConvert;

  @GetMapping("/infoByApplication")
  @ApiOperation("获取应用的服务信息")
  @ApiImplicitParam(name = "application", value = "应用ID", dataTypeClass = Long.class, required = true)
  public List<ApplicationServeInfo> infoByApplication(@RequestParam("application") ApplicationEntity application) {
    Map<String, String> serveToContainer = ConvertUtils.convert(applicationService.getComposeServeList(application),
        Collectors.toMap(ComposeServeInfo::getServeIndicate, ComposeServeInfo::getContainerId));
    List<ServeEntity> serveEntityList = serveService.listByApplicationId(application.getId());
    List<ApplicationServeInfo> result = new ArrayList<>();
    serveEntityList.forEach(serveEntity -> {
      String containerId = serveToContainer.get(serveEntity.getServeIndicate());
      Container container = containerService.getByName(containerId);
      ApplicationServeInfo info = ApplicationServeInfo.builder()
          .id(serveEntity.getId())
          .name(serveEntity.getName())
          .description(serveEntity.getDescription())
          .userId(serveEntity.getUserId())
          .portList(Arrays.stream(StrUtil.split(serveEntity.getPort(), ";")).map(Integer::parseInt).collect(Collectors.toSet()))
          .state(container == null ? ContainerState.EXITED.getName() : container.getState())
          .build();
      result.add(info);
    });
    return result;
  }

  @GetMapping("/search")
  @ApiOperation("根据名称搜索当前用户或共享的应用")
  @ApiImplicitParam(name = "name", value = "服务名称", dataTypeClass = String.class, required = true)
  public List<ServeNameVO> searchByName(@RequestParam(value = "name") String name) {
    return ConvertUtils.convert(serveService.listShareOrUserIdByName(SecurityUtils.getUserId(), name), serveConvert::convert);
  }
}
