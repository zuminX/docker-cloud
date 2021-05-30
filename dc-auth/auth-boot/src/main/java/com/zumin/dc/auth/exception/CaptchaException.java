package com.zumin.dc.auth.exception;

import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.common.core.enums.StatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import lombok.Getter;

/**
 * 验证码异常类
 */
@Getter
public class CaptchaException extends BaseException {

  private static final long serialVersionUID = -2676690140146545943L;

  /**
   * 验证码异常类的无参构造方法
   */
  public CaptchaException() {
    super(AuthStatusCode.CAPTCHA_ERROR);
  }

  /**
   * 验证码异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public CaptchaException(StatusCode statusCode) {
    super(statusCode);
  }
}
