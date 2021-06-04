package com.zumin.dc.dockerserve.validator;

import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 应用类的可访问性的验证器类
 */
public class CheckApplicationAccessValidator implements ConstraintValidator<CheckApplicationAccess, ApplicationEntity> {

  /**
   * 验证当前用户是否有权限访问该应用
   *
   * @param entity  应用
   * @param context 验证上下文
   * @return 若合法则返回true，否则返回false
   */
  @Override
  public boolean isValid(ApplicationEntity entity, ConstraintValidatorContext context) {
    return DockerServeUtils.checkAccess(entity);
  }
}
