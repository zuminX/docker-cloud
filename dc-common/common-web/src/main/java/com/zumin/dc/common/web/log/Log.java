package com.zumin.dc.common.web.log;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义操作日志记录注解
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

  /**
   * 日志标题
   */
  String value() default "";

  /**
   * 功能
   */
  BusinessType businessType() default BusinessType.INSERT;

  /**
   * 是否保存参数数据
   */
  boolean isSaveParameterData() default true;
}
