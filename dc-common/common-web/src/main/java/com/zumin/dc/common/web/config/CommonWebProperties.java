package com.zumin.dc.common.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "common.web")
public class CommonWebProperties {

  /**
   * 是否开启全局数据绑定
   */
  private Boolean dataBinding = true;

  /**
   * 是否开启全局异常处理
   */
  private Boolean exceptionHandler = true;

  /**
   * 是否开启全局响应处理
   */
  private Boolean response = true;

}
