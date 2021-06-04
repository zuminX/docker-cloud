package com.zumin.dc.dockerserve.validator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证镜像版本的验证器类
 */
public class IsVersionValidator implements ConstraintValidator<IsVersion, String> {

  /**
   * 验证该版本字符串是否合法
   *
   * @param version 版本字符串
   * @param context 验证上下文
   * @return 若合法则返回true，否则返回false
   */
  public boolean isValid(String version, ConstraintValidatorContext context) {
    if (StrUtil.isBlank(version)) {
      return false;
    }
    if (version.equals("latest")) {
      return true;
    }
    String[] versionNumber = version.split("\\.");
    if (versionNumber.length != 3) {
      return false;
    }
    return Arrays.stream(versionNumber).noneMatch(number -> !NumberUtil.isInteger(number) || Integer.parseInt(number) < 0);
  }
}
