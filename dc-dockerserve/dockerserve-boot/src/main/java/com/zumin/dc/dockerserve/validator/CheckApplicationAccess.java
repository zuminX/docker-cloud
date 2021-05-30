package com.zumin.dc.dockerserve.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import com.zumin.dc.common.web.validator.LocalDateRangeValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证应用程序类的可访问性注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckApplicationAccessValidator.class)
public @interface CheckApplicationAccess {

  String message() default "没有权限访问该应用程序";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
