package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * 应用异常类
 */
public class ApplicationException extends BaseException {

  private static final long serialVersionUID = -272869029922076172L;

  public ApplicationException() {
    super(DockerServeStatusCode.APPLICATION_ERROR);
  }

  public ApplicationException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
