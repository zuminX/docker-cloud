package com.zumin.dc.common.web.exception;

import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.common.web.enums.WebStatusCode;
import lombok.Getter;

/**
 * 表单参数转换异常类
 */
@Getter
public class FormParameterConversionException extends BaseException {

  private static final long serialVersionUID = 1166533932002631826L;

  /**
   * 转化字符串
   */
  private String convertString;

  /**
   * 目标Class对象
   */
  private Class<?> targetClass;

  /**
   * 表单参数转换异常类的无参构造方法
   */
  public FormParameterConversionException() {
    super(CommonStatusCode.FORM_PARAMETER_CONVERSION_ERROR);
  }

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param convertString 进行转换的字符串
   * @param targetClass   转换的Class对象
   */
  public FormParameterConversionException(String convertString, Class<?> targetClass) {
    super(CommonStatusCode.FORM_PARAMETER_CONVERSION_ERROR);
    this.convertString = convertString;
    this.targetClass = targetClass;
  }

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public FormParameterConversionException(WebStatusCode statusCode) {
    super(statusCode);
  }
}