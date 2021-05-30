package com.zumin.dc.common.web.validator;

import com.zumin.dc.common.web.domain.LocalDateTimeRange;
import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日期时间范围类的验证器类
 */
public class LocalDateTimeRangeValidator implements ConstraintValidator<IsLocalDateTimeRange, LocalDateTimeRange> {

  /**
   * 开始时间不能为空
   */
  private boolean startTimeNotNull;

  /**
   * 结束时间不能为空
   */
  private boolean endTimeNotNull;

  /**
   * 初始化校验器
   *
   * @param isLocalDateTimeRange 验证日期时间范围类的注解
   */
  @Override
  public void initialize(IsLocalDateTimeRange isLocalDateTimeRange) {
    this.startTimeNotNull = isLocalDateTimeRange.startNotNull();
    this.endTimeNotNull = isLocalDateTimeRange.endNotNull();
  }

  /**
   * 校验指定范围的开始的日期时间是否早于结束的日期时间
   *
   * @param range   待校验的范围对象
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(LocalDateTimeRange range, ConstraintValidatorContext context) {
    if (range == null) {
      return !startTimeNotNull && !endTimeNotNull;
    }
    LocalDateTime start = range.getStart(), end = range.getEnd();
    if (startTimeNotNull && start == null || endTimeNotNull && end == null) {
      return false;
    }
    return start == null || end == null || end.compareTo(start) >= 0;
  }
}
