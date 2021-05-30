package com.zumin.dc.common.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class CommonWebConfig {

  /**
   * 设置密码编码器
   *
   * @return 密码编码器
   */
  @Bean
  @Lazy
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
