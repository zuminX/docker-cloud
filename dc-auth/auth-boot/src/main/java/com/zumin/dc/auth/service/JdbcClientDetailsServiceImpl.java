package com.zumin.dc.auth.service;

import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * 实现获取客户端服务器的详情
 */
public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

  public JdbcClientDetailsServiceImpl(DataSource dataSource) {
    super(dataSource);
  }

  /**
   * 从数据库中加载客户端信息
   *
   * @param clientId 客户端ID
   * @return 客户端详情
   */
  @Override
  @SneakyThrows
  public ClientDetails loadClientByClientId(String clientId) {
    return super.loadClientByClientId(clientId);
  }
}
