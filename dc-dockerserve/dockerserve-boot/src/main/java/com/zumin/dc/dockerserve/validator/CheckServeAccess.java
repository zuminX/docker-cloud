package com.zumin.dc.dockerserve.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证服务类的可访问性注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckServeAccessValidator.class)
public @interface CheckServeAccess {

  String message() default "没有权限访问该服务";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
