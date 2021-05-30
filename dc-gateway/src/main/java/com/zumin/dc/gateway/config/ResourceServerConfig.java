package com.zumin.dc.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.gateway.security.AuthorizationManager;
import com.zumin.dc.gateway.utils.GatewayUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class ResourceServerConfig {

  private final AuthorizationManager authorizationManager;
  private final WhiteListConfig whiteListConfig;

  /**
   * 自定义处理WebSecurity
   *
   * @param http 安全对象
   * @return 调用链
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.oauth2ResourceServer().jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
    http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
    http.authorizeExchange()
        .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(), String.class)).permitAll()
        .anyExchange().access(authorizationManager)
        .and()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint())
        .and().csrf().disable();
    return http.build();
  }

  /**
   * 处理未授权
   *
   * @return 服务被拒绝的处理器
   */
  @Bean
  public ServerAccessDeniedHandler accessDeniedHandler() {
    return (exchange, denied) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
        .flatMap(response -> GatewayUtils.writeFailedToResponse(response, AuthStatusCode.ACCESS_UNAUTHORIZED));
  }

  /**
   * 处理token无效或者已过期自定义响应
   *
   * @return 服务器身份验证入口点
   */
  @Bean
  public ServerAuthenticationEntryPoint authenticationEntryPoint() {
    return (exchange, e) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
        .flatMap(response -> GatewayUtils.writeFailedToResponse(response, AuthStatusCode.TOKEN_INVALID_OR_EXPIRED));
  }

  /**
   * 把jwt的Claim中的authorities加入ServerHttpSecurity中的Authentication
   *
   * @return 转换器
   */
  @Bean
  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.JWT_AUTHORITIES_KEY);

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }
}
