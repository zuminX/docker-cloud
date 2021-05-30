package com.zumin.dc.common.web.validator;

import com.zumin.dc.common.web.domain.LocalDateRange;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日期范围类的验证器类
 */
public class LocalDateRangeValidator implements ConstraintValidator<IsLocalDateRange, LocalDateRange> {

  /**
   * 开始日期不能为空
   */
  private boolean startDateNotNull;

  /**
   * 结束日期不能为空
   */
  private boolean endDateNotNull;

  /**
   * 初始化校验器
   *
   * @param isLocalDateRange 验证日期范围类的注解
   */
  @Override
  public void initialize(IsLocalDateRange isLocalDateRange) {
    this.startDateNotNull = isLocalDateRange.startNotNull();
    this.endDateNotNull = isLocalDateRange.endNotNull();
  }

  /**
   * 校验指定范围的开始的日期是否早于结束的日期
   *
   * @param range   待校验的范围对象
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(LocalDateRange range, ConstraintValidatorContext context) {
    if (range == null) {
      return !startDateNotNull && !endDateNotNull;
    }
    LocalDate start = range.getStart(), end = range.getEnd();
    if (startDateNotNull && start == null || endDateNotNull && end == null) {
      return false;
    }
    return start == null || end == null || end.compareTo(start) >= 0;
  }
}
