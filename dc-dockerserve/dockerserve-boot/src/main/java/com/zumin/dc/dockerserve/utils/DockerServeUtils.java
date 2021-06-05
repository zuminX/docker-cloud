package com.zumin.dc.dockerserve.utils;

import com.alibaba.spring.util.FieldUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import lombok.experimental.UtilityClass;

/**
 * DockerServe模块的工具类
 */
@UtilityClass
public class DockerServeUtils {

  /**
   * 检查该对象的可访问性
   *
   * @param object 对象
   * @return 若可访问则返回true，否则返回false
   */
  public boolean checkAccess(Object object) {
    if (object == null) {
      return false;
    }
    if (SecurityUtils.isAdmin()) {
      return true;
    }
    Object share = FieldUtils.getFieldValue(object, "share");
    if (share instanceof Boolean && (Boolean) share) {
      return true;
    }
    Object userId = FieldUtils.getFieldValue(object, "userId");
    return userId instanceof Long && SecurityUtils.getUserId().equals(userId);
  }


  /**
   * 检查端口是否合法
   *
   * @param port 端口号
   * @return 若合法则返回true，否则返回false
   */
  public boolean checkPort(Integer port) {
    return port != null && port > 0 && port <= 60999;
  }
}
