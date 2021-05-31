package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * 服务异常类
 */
public class ServeException extends BaseException {

  private static final long serialVersionUID = 7654861180644758881L;

  public ServeException() {
    super(DockerServeStatusCode.SERVE_ERROR);
  }

  public ServeException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
