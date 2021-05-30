package com.zumin.dc.common.mybatis.excpetion;

import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.common.mybatis.enums.MybatisStatusCode;
import lombok.Getter;

/**
 * 分页异常类
 */
@Getter
public class PageException extends BaseException {

  private static final long serialVersionUID = -1202049169478818925L;

  /**
   * 分页异常类的无参构造方法
   */
  public PageException() {
    super(MybatisStatusCode.PAGE_ERROR);
  }

  /**
   * 分页异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public PageException(MybatisStatusCode statusCode) {
    super(statusCode);
  }

}
