package com.zumin.dc.dockerserve.validator;

import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 服务类的可访问性的验证器类
 */
public class CheckServeAccessValidator implements ConstraintValidator<CheckServeAccess, ServeEntity> {

  /**
   * 验证当前用户是否有权限访问该服务
   *
   * @param entity  服务
   * @param context 验证上下文
   * @return 若合法则返回true，否则返回false
   */
  @Override
  public boolean isValid(ServeEntity entity, ConstraintValidatorContext context) {
    return DockerServeUtils.checkAccess(entity);
  }
}
