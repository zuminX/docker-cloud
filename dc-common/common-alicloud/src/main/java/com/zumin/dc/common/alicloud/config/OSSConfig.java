package com.zumin.dc.common.alicloud.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS配置类
 */
@Configuration
public class OSSConfig {

  @Value("${alicloud.access-key-id}")
  private String accessKeyId;
  @Value("${alicloud.secret-key-secret}")
  private String secretKeySecret;
  @Value("${alicloud.oss.endpoint}")
  private String endpoint;

  /**
   * 实例化一个OSS对象到容器中
   *
   * @return OSS对象
   */
  @Bean
  public OSS oss() {
    return new OSSClientBuilder().build(endpoint, accessKeyId, secretKeySecret);
  }
}
