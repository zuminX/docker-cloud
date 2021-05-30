package com.zumin.dc.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "common.core")
public class CommonCoreProperties {

  /**
   * 项目包名
   */
  private String projectPackage = "com.zumin.dc";
}
