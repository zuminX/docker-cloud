package com.zumin.dc.common.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证日期范围类的注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LocalDateRangeValidator.class)
public @interface IsLocalDateRange {

  String message() default "日期范围不合法";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * 若该属性为false，则对于任意一个开始日期为null的日期范围对象而言，校验都会通过
   *
   * @return 开始日期是否不能为null
   */
  boolean startNotNull() default false;

  /**
   * 若该属性为false，则对于任意一个结束日期为null的日期范围对象而言，校验都会通过
   *
   * @return 结束日期是否不能为null
   */
  boolean endNotNull() default false;
}
