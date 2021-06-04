package com.zumin.dc.dockerserve.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dockerjava.api.model.Container;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.enums.ContainerState;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ServeException;
import com.zumin.dc.dockerserve.pojo.body.RequestServeBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.service.ContainerService;
import com.zumin.dc.dockerserve.service.ReverseProxyService;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@ComRestController(path = ReverseProxyController.PREFIX, tags = "反向代理API接口")
public class ReverseProxyController {

  protected final static String PREFIX = "/proxy";
  private final ReverseProxyService reverseProxyService;
  private final ServeService serveService;
  private final ContainerService containerService;
  @Value("${proxy.ip}")
  private String ip;

  @ApiOperation("反向代理访问服务的请求")
  @ApiImplicitParam(name = "request", value = "HTTP请求", dataTypeClass = HttpServletRequest.class, required = true)
  @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, produces = MediaType.ALL_VALUE)
  public ResponseEntity<?> proxyAll(HttpServletRequest request) {
    RequestServeBody requestServe = resolveRequestServe(request.getRequestURI());
    if (requestServe == null) {
      throw new ServeException(DockerServeStatusCode.SERVE_PATH_ILLEGAL);
    }
    ServeEntity entity = serveService.getById(requestServe.getServeId());
    assertServe(entity);
    Map<Integer, Integer> portMap = containerService.getPortMap(getContainer(entity));
    assertPort(requestServe, portMap);
    return reverseProxyService.proxy("http://" + ip + ":" + portMap.get(requestServe.getPort()), PREFIX + requestServe.toPath());
  }

  /**
   * 检查服务的可访问性
   *
   * @param entity 服务
   */
  private void assertServe(ServeEntity entity) {
    if (!DockerServeUtils.checkAccess(entity)) {
      throw new ServeException(DockerServeStatusCode.SERVE_UNAUTHORIZED_ACCESS);
    }
  }

  /**
   * 检查端口的可访问性
   *
   * @param requestServe 请求服务
   * @param portMap      内部端口->访问端口的映射
   */
  private void assertPort(RequestServeBody requestServe, Map<Integer, Integer> portMap) {
    if (portMap.get(requestServe.getPort()) == null) {
      throw new ServeException(DockerServeStatusCode.SERVE_ACCESS_PORT_ILLEGAL);
    }
  }

  /**
   * 获取服务对应的容器
   *
   * @param entity 服务
   * @return 容器
   */
  private Container getContainer(ServeEntity entity) {
    Container container = containerService.getByName(entity.getContainerName());
    if (container == null || !ContainerState.isRunning(container.getState())) {
      throw new ServeException(DockerServeStatusCode.SERVE_NOT_START);
    }
    return container;
  }

  /**
   * 解析请求服务的信息
   *
   * @param uri 请求URI
   * @return 若能解析则返回请求服务的信息对象，否则返回null
   */
  private RequestServeBody resolveRequestServe(String uri) {
    uri = StrUtil.removePrefix(uri, PREFIX);
    if (StrUtil.isBlank(uri)) {
      return null;
    }
    if (uri.charAt(0) != '/') {
      return null;
    }
    int bodyEndIndex = StrUtil.indexOf(uri, '/', 1);
    if (bodyEndIndex == -1) {
      bodyEndIndex = uri.length();
    }
    String requestServeBodyStr = StrUtil.sub(uri, 1, bodyEndIndex);
    int splitIndex = requestServeBodyStr.indexOf(":");
    if (splitIndex == -1) {
      return null;
    }
    Long serveId = resolveServeId(requestServeBodyStr.substring(0, splitIndex));
    Integer port = resolvePort(requestServeBodyStr.substring(splitIndex + 1));
    if (serveId == null || port == null) {
      return null;
    }
    return new RequestServeBody(serveId, port);
  }

  /**
   * 解析端口号
   *
   * @param str 端口号字符串
   * @return 端口号
   */
  private Integer resolvePort(String str) {
    if (!NumberUtil.isInteger(str)) {
      return null;
    }
    Integer port = Integer.valueOf(str);
    return !DockerServeUtils.checkPort(port) ? null : port;
  }

  /**
   * 解析服务ID
   *
   * @param str 服务字符串
   * @return 服务ID
   */
  private Long resolveServeId(String str) {
    if (!NumberUtil.isLong(str)) {
      return null;
    }
    long serveId = Long.parseLong(str);
    return serveId <= 0 ? null : serveId;
  }
}