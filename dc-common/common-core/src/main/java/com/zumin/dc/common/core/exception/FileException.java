package com.zumin.dc.common.core.exception;

import com.zumin.dc.common.core.enums.CommonStatusCode;

/**
 * 文件异常类
 */
public class FileException extends BaseException {

  private static final long serialVersionUID = -658002836273149602L;

  public FileException() {
    super(CommonStatusCode.FILE_ERROR);
  }

  public FileException(CommonStatusCode commonStatusCode) {
    super(commonStatusCode);
  }

  public FileException(Throwable cause) {
    super(CommonStatusCode.FILE_ERROR, cause);
  }

}
