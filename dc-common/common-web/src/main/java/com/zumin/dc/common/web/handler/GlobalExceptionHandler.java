package com.zumin.dc.common.web.handler;

import static com.zumin.dc.common.core.enums.CommonStatusCode.ERROR;
import static com.zumin.dc.common.core.enums.CommonStatusCode.INVALID_REQUEST_PARAM_ERROR;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.exception.FormParameterConversionException;
import java.io.Serializable;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.exception-handler'])}")
public class GlobalExceptionHandler {

  private static final String INVALID_PARAM_TIP_TEMPLATE = "{}：{}";

  /**
   * 处理所有异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = Exception.class)
  public CommonResult<Exception> exceptionHandler(Exception e) {
    log.error("[Exception]", e);
    return CommonResult.error(ERROR);
  }

  /**
   * 处理基础异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BaseException.class)
  public CommonResult<BaseException> baseExceptionHandler(BaseException e) {
    log.debug("[" + e.getClass().getSimpleName() + "]", e);
    return CommonResult.error(e.getStatusCode());
  }

  /**
   * 处理流量控制异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BlockException.class)
  public CommonResult<BlockException> baseExceptionHandler(BlockException e) {
    log.debug("[" + e.getClass().getSimpleName() + "]", e);
    return CommonResult.error(CommonStatusCode.ACCESS_DENIED);
  }

  /**
   * 处理表单参数转换异常
   *
   * @param e 登录异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = FormParameterConversionException.class)
  public CommonResult<FormParameterConversionException> formParameterConversionExceptionHandler(FormParameterConversionException e) {
    log.warn("[FormParameterConversionException]", e);
    return CommonResult.error(e.getStatusCode());
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public CommonResult<ConstraintViolationException> constraintViolationExceptionHandler(ConstraintViolationException e) {
    log.debug("[FormParameterConversionException]", e);
    return buildInvalidParamErrorCommonResult(
        ConvertUtils.convertAndJoin(e.getConstraintViolations(), ConstraintViolation::getMessage, ";"));
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BindException.class)
  public CommonResult<java.net.BindException> bindExceptionHandler(BindException e) {
    log.debug("[ParameterVerificationException]", e);
    return buildInvalidParamErrorCommonResult(ConvertUtils.convertAndJoin(e.getAllErrors(), ObjectError::getDefaultMessage, ";"));
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public CommonResult<MethodArgumentNotValidException> bindExceptionHandler(MethodArgumentNotValidException e) {
    log.debug("[ParameterVerificationException]", e);
    return buildInvalidParamErrorCommonResult(
        ConvertUtils.convertAndJoin(e.getBindingResult().getAllErrors(), DefaultMessageSourceResolvable::getDefaultMessage, ";"));
  }

  /**
   * 构建无效的参数的返回结果
   *
   * @param error 异常原因
   * @param <T>   异常类型
   * @return 经过包装的结果对象
   */
  private <T extends Serializable> CommonResult<T> buildInvalidParamErrorCommonResult(String error) {
    String tip = null;
    if (StrUtil.isNotBlank(error)) {
      tip = StrUtil.format(INVALID_PARAM_TIP_TEMPLATE, INVALID_REQUEST_PARAM_ERROR.getMessage(), error);
    }
    return CommonResult.error(INVALID_REQUEST_PARAM_ERROR, tip);
  }
}
