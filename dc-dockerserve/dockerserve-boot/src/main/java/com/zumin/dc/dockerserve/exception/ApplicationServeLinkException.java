package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * Docker应用程序服务链接异常类
 */
public class ApplicationServeLinkException extends BaseException {

  private static final long serialVersionUID = 7222862949046799438L;

  public ApplicationServeLinkException() {
    super(DockerServeStatusCode.SERVE_LINK_ERROR);
  }

  public ApplicationServeLinkException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
