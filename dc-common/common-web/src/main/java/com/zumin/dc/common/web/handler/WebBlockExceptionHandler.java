package com.zumin.dc.common.web.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * 自定义流量控制异常处理器
 */
@Component
public class WebBlockExceptionHandler implements BlockExceptionHandler {

  /**
   * 直接抛出流量控制异常，让GlobalExceptionHandler处理异常
   *
   * @param httpServletRequest  请求对象
   * @param httpServletResponse 响应对象
   * @param e                   流量控制异常
   * @throws Exception 异常
   */
  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
    throw e;
  }
}
