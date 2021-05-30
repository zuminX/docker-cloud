package com.zumin.dc.dockerserve.validator;

import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 应用程序类的可访问性的验证器类
 */
public class CheckApplicationAccessValidator implements ConstraintValidator<CheckApplicationAccess, ApplicationEntity> {

  @Override
  public boolean isValid(ApplicationEntity entity, ConstraintValidatorContext context) {
    return DockerServeUtils.checkAccess(entity);
  }
}
