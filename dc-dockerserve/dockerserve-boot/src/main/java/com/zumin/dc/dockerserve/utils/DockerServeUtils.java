package com.zumin.dc.dockerserve.utils;

import com.alibaba.spring.util.FieldUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import lombok.experimental.UtilityClass;

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
    //TODO 管理员无须受此限制
    Object userId = FieldUtils.getFieldValue(object, "userId");
    if (userId instanceof Long && SecurityUtils.getUserId().equals(userId)) {
      return true;
    }
    Object share = FieldUtils.getFieldValue(object, "share");
    return share instanceof Boolean ? (Boolean) share : Boolean.valueOf(false);
  }
}
