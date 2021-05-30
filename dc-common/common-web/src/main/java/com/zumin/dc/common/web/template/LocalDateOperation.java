package com.zumin.dc.common.web.template;

import java.time.LocalDate;
import org.jetbrains.annotations.NotNull;

/**
 * LocalDate操作的接口
 */
public interface LocalDateOperation {

  /**
   * 返回日期加上增加量的副本
   *
   * @param date        日期
   * @param amountToAdd 增加量
   * @return 给定日期增加给定增量后的日期
   */
  LocalDate plus(@NotNull LocalDate date, long amountToAdd);

  /**
   * 获取该日期的第一天
   *
   * @param date 日期
   * @return 该日期的第一天
   */
  LocalDate getFirst(@NotNull LocalDate date);

  /**
   * 获取该日期的下一个日期
   *
   * @param date 日期
   * @return 给定日期的下一个日期
   */
  default LocalDate next(@NotNull LocalDate date) {
    return plus(date, 1L);
  }

  /**
   * 返回日期减去减少量的副本
   *
   * @param date             日期
   * @param amountToSubtract 减少量
   * @return 给定日期减去给定减量后的日期
   */
  default LocalDate minus(@NotNull LocalDate date, long amountToSubtract) {
    return plus(date, -amountToSubtract);
  }

}
