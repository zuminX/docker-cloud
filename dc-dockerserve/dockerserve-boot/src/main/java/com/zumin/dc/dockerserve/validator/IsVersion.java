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
 * 验证镜像版本的合法性注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsVersionValidator.class)
public @interface IsVersion {

  String message() default "镜像版本的格式不符合要求";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
