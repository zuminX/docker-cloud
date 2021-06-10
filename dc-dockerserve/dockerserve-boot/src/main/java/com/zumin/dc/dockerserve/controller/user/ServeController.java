package com.zumin.dc.dockerserve.controller.user;

import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ServeConvert;
import com.zumin.dc.dockerserve.enums.ContainerState;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ServeException;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeBasicVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeVO;
import com.zumin.dc.dockerserve.service.ContainerService;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.validator.CheckServeAccess;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/serve", tags = "服务API接口")
public class ServeController extends BaseController {

  private final ContainerService containerService;
  private final ServeService serveService;
  private final ServeConvert serveConvert;

  @GetMapping("/infoByApplication")
  @ApiOperation("获取应用的服务信息")
  @ApiImplicitParam(name = "application", value = "应用ID", dataTypeClass = Long.class, required = true)
  public List<ServeVO> infoByApplication(@RequestParam("application") ApplicationEntity application) {
    List<ServeEntity> serveEntityList = serveService.listByApplicationId(application.getId());
    return ConvertUtils.convert(serveEntityList, serveEntity -> serveConvert.convertToVO(serveEntity, getServeState(serveEntity)));
  }

  @ApiOperation("获取用户所有服务的简要信息")
  @GetMapping("/basicInfo")
  public List<ServeBasicVO> basicInfo() {
    List<ServeEntity> serveEntityList = serveService.listByUserId(SecurityUtils.getUserId());
    return ConvertUtils.convert(serveEntityList, serveEntity -> new ServeBasicVO(serveEntity.getName(), getServeState(serveEntity)));
  }

  @GetMapping("/search")
  @ApiOperation("根据名称搜索当前用户或共享的服务")
  @ApiImplicitParam(name = "name", value = "服务名称", dataTypeClass = String.class, required = true)
  public List<ServeNameVO> searchByName(@RequestParam("name") String name) {
    return ConvertUtils.convert(serveService.listShareOrUserIdByName(SecurityUtils.getUserId(), name), serveConvert::convertToNameVO);
  }

  @ApiOperation("获取访问该服务的路径令牌")
  @GetMapping("/accessToken")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "serve", value = "服务ID", dataTypeClass = Long.class, required = true),
      @ApiImplicitParam(name = "port", value = "端口号", dataTypeClass = Integer.class, required = true)
  })
  public CommonResult<String> generateAccessToken(@RequestParam("serve") @CheckServeAccess ServeEntity serve, @RequestParam("port") Integer port) {
    Map<Integer, Integer> portMap = containerService.getPortMap(getContainer(serve));
    assertPort(port, portMap);
    return CommonResult.success(serveService.generateAccessToken(portMap.get(port))) ;
  }

  /**
   * 获取服务当前的状态
   *
   * @param serveEntity 服务
   * @return 服务对应Docker容器的状态
   */
  private String getServeState(ServeEntity serveEntity) {
    Container container = containerService.getByName(serveEntity.getServeIndicate());
    return container == null ? ContainerState.EXITED.getName() : container.getState();
  }

  /**
   * 获取服务对应的容器
   *
   * @param entity 服务
   * @return 容器
   */
  private Container getContainer(ServeEntity entity) {
    Container container = containerService.getByName(entity.getServeIndicate());
    if (container == null || !ContainerState.isRunning(container.getState())) {
      throw new ServeException(DockerServeStatusCode.SERVE_NOT_START);
    }
    return container;
  }

  /**
   * 检查端口的可访问性
   *
   * @param requestPort 请求端口
   * @param portMap     内部端口->访问端口的映射
   */
  private void assertPort(Integer requestPort, Map<Integer, Integer> portMap) {
    if (portMap.get(requestPort) == null) {
      throw new ServeException(DockerServeStatusCode.SERVE_ACCESS_PORT_ILLEGAL);
    }
  }

}
