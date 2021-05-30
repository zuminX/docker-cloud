package com.zumin.dc.auth.handler;

import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.common.core.result.CommonResult;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证异常处理
 */
@RestControllerAdvice
public class AuthExceptionHandler {

  /**
   * 处理用户名和密码异常
   *
   * @param e 无效认证异常
   * @return 返回结果
   */
  @ExceptionHandler(InvalidGrantException.class)
  public CommonResult<?> handleInvalidGrantException(InvalidGrantException e) {
    return CommonResult.error(AuthStatusCode.USERNAME_OR_PASSWORD_ERROR);
  }


  /**
   * 处理账户异常(禁用、锁定、过期)
   *
   * @param e 内部认证异常
   * @return 返回结果
   */
  @ExceptionHandler({InternalAuthenticationServiceException.class})
  public CommonResult<?> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
    return CommonResult.error(AuthStatusCode.ACCOUNT_ERROR, e.getMessage());
  }

}
