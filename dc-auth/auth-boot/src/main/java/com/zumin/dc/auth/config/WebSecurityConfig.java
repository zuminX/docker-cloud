package com.zumin.dc.auth.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web安全配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * 配置验证路径及验证方式
   *
   * @param http Http安全对象
   * @throws Exception 异常
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
        .and()
        .authorizeRequests().antMatchers("/getPublicKey", "/oauth/logout", "/captcha/captchaImage", "/captcha/check").permitAll()
        .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
        .anyRequest().authenticated()
        .and()
        .csrf().disable();
  }

  /**
   * 阻止SpringBoot自动配置一个AuthenticationManager
   */
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
