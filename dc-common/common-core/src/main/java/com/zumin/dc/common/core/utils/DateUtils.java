package com.zumin.dc.common.core.utils;

import cn.hutool.core.date.DateUtil;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * 时间工具类
 */
@UtilityClass
public class DateUtils {

  /**
   * +8的时区
   */
  private final ZoneOffset ZONE = ZoneOffset.of("+8");

  /**
   * 简单的日期字符串，即格式为年月日
   *
   * @return 日期字符串
   */
  public String plainDateStr() {
    return DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
  }

  /**
   * 获取两个日期时间的差值
   *
   * @param dateTime1 日期时间一
   * @param dateTime2 日期时间二
   * @return 以ms为单位的差值
   */
  public long computeDiff(@NotNull LocalDateTime dateTime1, @NotNull LocalDateTime dateTime2) {
    return toTimestamp(dateTime1) - toTimestamp(dateTime2);
  }

  /**
   * 获取两个日期时间的绝对差值
   *
   * @param dateTime1 日期时间一
   * @param dateTime2 日期时间二
   * @return 以ms为单位的差值
   */
  public long computeAbsDiff(@NotNull LocalDateTime dateTime1, @NotNull LocalDateTime dateTime2) {
    return Math.abs(computeDiff(dateTime1, dateTime2));
  }

  /**
   * 将本地日期时间转换时间戳
   *
   * @param localDateTime 本地日期时间
   * @return 对应的时间戳
   */
  public long toTimestamp(@NotNull LocalDateTime localDateTime) {
    return localDateTime.toInstant(ZONE).toEpochMilli();
  }
}
