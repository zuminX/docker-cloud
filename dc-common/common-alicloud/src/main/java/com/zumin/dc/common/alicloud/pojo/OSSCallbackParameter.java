package com.zumin.dc.common.alicloud.pojo;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * oss上传文件的回调参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OSSCallbackParameter<T> {

  /**
   * 回调路径
   */
  private String callbackPath;

  /**
   * 回调数据
   */
  private T data;

  /**
   * 回调数据的过期时间单位
   */
  private TimeUnit timeUnit;

  /**
   * 回调数据的过期时间
   */
  private long timeout;

  public OSSCallbackParameter(String callbackPath, T data) {
    this.callbackPath = callbackPath;
    this.data = data;
    this.timeout = 10;
    this.timeUnit = TimeUnit.MINUTES;
  }
}
