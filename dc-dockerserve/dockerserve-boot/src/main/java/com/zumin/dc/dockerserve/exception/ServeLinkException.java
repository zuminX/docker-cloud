package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * 服务链接异常类
 */
public class ServeLinkException extends BaseException {

  private static final long serialVersionUID = 7222862949046799438L;

  public ServeLinkException() {
    super(DockerServeStatusCode.SERVE_LINK_ERROR);
  }

  public ServeLinkException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
