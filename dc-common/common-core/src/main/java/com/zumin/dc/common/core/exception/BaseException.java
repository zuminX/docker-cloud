package com.zumin.dc.common.core.exception;

import com.zumin.dc.common.core.enums.StatusCode;
import lombok.Getter;

/**
 * 基础异常类
 */
@Getter
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = -8105492018397966401L;

  /**
   * 异常编号
   */
  private final StatusCode statusCode;

  public BaseException(StatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public BaseException(StatusCode statusCode, Throwable cause) {
    super(cause);
    this.statusCode = statusCode;
  }
}
