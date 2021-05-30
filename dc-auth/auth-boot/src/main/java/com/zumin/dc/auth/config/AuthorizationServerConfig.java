package com.zumin.dc.auth.config;

import com.zumin.dc.auth.domain.User;
import com.zumin.dc.auth.filter.CustomClientCredentialsTokenEndpointFilter;
import com.zumin.dc.auth.service.JdbcClientDetailsServiceImpl;
import com.zumin.dc.auth.service.UserDetailsServiceImpl;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.core.auth.AuthParamName;
import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.core.utils.ServletUtils;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 授权服务配置
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final DataSource dataSource;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;

  /**
   * 配置客户端详情
   */
  @Override
  @SneakyThrows
  public void configure(ClientDetailsServiceConfigurer clients) {
    JdbcClientDetailsServiceImpl jdbcClientDetailsService = new JdbcClientDetailsServiceImpl(dataSource);
    jdbcClientDetailsService.setFindClientDetailsSql(AuthConstants.FIND_CLIENT_DETAILS_SQL);
    jdbcClientDetailsService.setSelectClientDetailsSql(AuthConstants.SELECT_CLIENT_DETAILS_SQL);
    clients.withClientDetails(jdbcClientDetailsService);
  }

  /**
   * 配置授权以及令牌的访问端点和令牌服务
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
    tokenEnhancers.add(tokenEnhancer());
    tokenEnhancers.add(jwtAccessTokenConverter());
    tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

    endpoints.authenticationManager(authenticationManager)
        .accessTokenConverter(jwtAccessTokenConverter())
        .tokenEnhancer(tokenEnhancerChain)
        .userDetailsService(userDetailsService)
        .reuseRefreshTokens(true);
  }

  /**
   * 配置安全配置器
   *
   * @param security 授权服务器安全配置器
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
    endpointFilter.afterPropertiesSet();
    endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
    security.addTokenEndpointAuthenticationFilter(endpointFilter);

    security.authenticationEntryPoint(authenticationEntryPoint())
        .tokenKeyAccess("isAuthenticated()")
        .checkTokenAccess("permitAll()");
  }

  /**
   * 自定义认证异常响应数据
   *
   * @return 认证入口点
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, e) -> {
      e.printStackTrace();
      ServletUtils.returnJsonData(response, CommonResult.error(AuthStatusCode.CLIENT_AUTHENTICATION_FAILED));
    };
  }

  /**
   * 使用非对称加密算法对token签名
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setKeyPair(keyPair());
    return converter;
  }

  /**
   * 从classpath下的密钥库中获取密钥对(公钥+私钥)
   */
  @Bean
  public KeyPair keyPair() {
    KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("sudoku.jks"), "2233qqaazz".toCharArray());
    return factory.getKeyPair("sudoku", "2233qqaazz".toCharArray());
  }

  /**
   * JWT内容增强
   */
  @Bean
  public TokenEnhancer tokenEnhancer() {
    return (accessToken, authentication) -> {
      Map<String, Object> map = new HashMap<>(8);
      User user = (User) authentication.getUserAuthentication().getPrincipal();
      map.put(AuthParamName.USER_ID, user.getId());
      map.put(AuthParamName.CLIENT_ID, user.getClientId());
      map.put(AuthParamName.USERNAME, user.getUsername());
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
      return accessToken;
    };
  }
}
