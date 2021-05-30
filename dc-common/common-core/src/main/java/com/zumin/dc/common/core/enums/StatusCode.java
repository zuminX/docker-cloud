package com.zumin.dc.common.core.enums;

import cn.hutool.core.util.StrUtil;

public interface StatusCode {

  int getNumber();

  String getMessage();

  default String getCode() {
    return StrUtil.removeSuffix(this.getClass().getSimpleName(), "StatusCode") + "-" + getNumber();
  }
}
