package com.zumin.dc.common.mybatis.enums;

import com.zumin.dc.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Mybatis响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum MybatisStatusCode implements StatusCode {

  PAGE_ERROR(10, "获取查询分页数据时发生错误"),
  PAGE_SIZE_ILLEGAL(11, "设置每页查询数据的条数非法"),
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
