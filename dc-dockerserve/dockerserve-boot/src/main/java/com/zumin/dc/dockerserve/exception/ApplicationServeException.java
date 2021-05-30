package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * Docker应用程序服务异常类
 */
public class ApplicationServeException extends BaseException {

  private static final long serialVersionUID = 7654861180644758881L;

  public ApplicationServeException() {
    super(DockerServeStatusCode.SERVE_ERROR);
  }

  public ApplicationServeException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
