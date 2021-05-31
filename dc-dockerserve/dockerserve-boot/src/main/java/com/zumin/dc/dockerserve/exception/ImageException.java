package com.zumin.dc.dockerserve.exception;

import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;

/**
 * 镜像异常类
 */
public class ImageException extends BaseException {

  private static final long serialVersionUID = 246223370731886088L;

  public ImageException() {
    super(DockerServeStatusCode.IMAGE_ERROR);
  }

  public ImageException(DockerServeStatusCode statusCode) {
    super(statusCode);
  }
}
