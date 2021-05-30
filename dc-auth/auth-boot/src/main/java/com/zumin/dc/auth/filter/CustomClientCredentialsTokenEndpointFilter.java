package com.zumin.dc.auth.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 重写filter实现客户端自定义异常处理
 */
@RequiredArgsConstructor
public class CustomClientCredentialsTokenEndpointFilter extends ClientCredentialsTokenEndpointFilter {

  private final AuthorizationServerSecurityConfigurer configurer;
  private AuthenticationEntryPoint authenticationEntryPoint;

  /**
   * 自定义身份认证端点
   *
   * @param authenticationEntryPoint 身份认证端点
   */
  @Override
  public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
    super.setAuthenticationEntryPoint(null);
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  /**
   * 获取身份验证管理器
   *
   * @return 身份验证管理器
   */
  @Override
  protected AuthenticationManager getAuthenticationManager() {
    return configurer.and().getSharedObject(AuthenticationManager.class);
  }

  /**
   * 配置后处理器
   */
  @Override
  public void afterPropertiesSet() {
    setAuthenticationFailureHandler((request, response, e) -> authenticationEntryPoint.commence(request, response, e));
    setAuthenticationSuccessHandler((request, response, authentication) -> {
    });
  }
}
