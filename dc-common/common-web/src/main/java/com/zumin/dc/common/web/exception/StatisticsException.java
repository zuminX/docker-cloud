package com.zumin.dc.common.web.exception;

import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.common.web.enums.WebStatusCode;
import lombok.Getter;

/**
 * 统计异常类
 */
@Getter
public class StatisticsException extends BaseException {

  private static final long serialVersionUID = 5955311444611029160L;

  /**
   * 统计异常类的无参构造方法
   */
  public StatisticsException() {
    super(WebStatusCode.STATISTICS_ERROR);
  }

  /**
   * 统计异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public StatisticsException(WebStatusCode statusCode) {
    super(statusCode);
  }
}
