package com.zumin.dc.common.web.enums;

import com.zumin.dc.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Web响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum WebStatusCode implements StatusCode {

  STATISTICS_ERROR(10, "统计信息时发生错误"),
  STATISTICS_INQUIRY_DATE_INVALID(11, "查询统计信息的日期无效"),
  ;

  /**
   * 编号
   */
  private final int number;

  /**
   * 错误信息
   */
  private final String message;
}
