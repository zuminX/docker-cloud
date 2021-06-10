package com.zumin.dc.dockerserve.controller;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ServeException;
import com.zumin.dc.dockerserve.service.ReverseProxyService;
import com.zumin.dc.dockerserve.service.ServeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@ComRestController(path = ReverseProxyController.PREFIX, tags = "反向代理API接口")
public class ReverseProxyController {

  protected final static String PREFIX = "/proxy";
  private final ReverseProxyService reverseProxyService;
  private final ServeService serveService;
  @Value("${proxy.ip}")
  private String ip;

  @ApiOperation("反向代理访问服务的请求")
  @ApiImplicitParam(name = "request", value = "HTTP请求", dataTypeClass = HttpServletRequest.class, required = true)
  @RequestMapping(value = "/{accessToken}/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, produces =
      MediaType.ALL_VALUE)
  public ResponseEntity<?> proxyAll(@PathVariable("accessToken") String accessToken) {
    Integer port = serveService.getAccessPort(accessToken);
    try {
      return reverseProxyService.proxy("http://" + ip + ":" + port, PREFIX + "/" + accessToken);
    } catch (Exception e) {
      throw new ServeException(DockerServeStatusCode.SERVE_REQUEST_FAILED);
    }
  }
}