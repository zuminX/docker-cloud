package com.zumin.dc.common.core.auth;

/**
 * 认证常量类
 */
public interface AuthConstants {

  /**
   * 认证请求头key
   */
  String AUTHORIZATION_KEY = "Authorization";

  /**
   * JWT令牌前缀
   */
  String AUTHORIZATION_PREFIX = "bearer ";

  /**
   * Basic认证前缀
   */
  String BASIC_PREFIX = "Basic ";

  /**
   * JWT载体key
   */
  String JWT_PAYLOAD_KEY = "payload";

  /**
   * JWT ID 唯一标识
   */
  String JWT_JTI = "jti";

  /**
   * JWT ID 唯一标识
   */
  String JWT_EXP = "exp";

  String CLIENT_DETAILS_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
      + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
      + "refresh_token_validity, additional_information, autoapprove";

  String BASE_CLIENT_DETAILS_SQL = "select " + CLIENT_DETAILS_FIELDS + " from oauth_client_details";

  String FIND_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " order by client_id";

  String SELECT_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " where client_id = ?";

  /**
   * JWT存储权限前缀
   */
  String AUTHORITY_PREFIX = "ROLE_";

  /**
   * JWT存储权限属性
   */
  String JWT_AUTHORITIES_KEY = "authorities";

  /**
   * 系统用户客户端ID
   */
  String USER_CLIENT_ID = "dc-ums";

  /**
   * 系统微信小程序客户端ID
   */
  String WEAPP_CLIENT_ID = "dc-weapp";

  String LOGOUT_PATH = "/dc-auth/oauth/logout";

  /**
   * Redis缓存权限规则key
   */
  String RESOURCE_ROLES_KEY = "auth:resource:roles";

}
