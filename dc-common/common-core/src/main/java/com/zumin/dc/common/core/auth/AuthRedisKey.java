package com.zumin.dc.common.core.auth;

/**
 * Redis键
 */
public interface AuthRedisKey {

  /**
   * 黑名单token前缀
   */
  String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

  /**
   * 验证码前缀
   */
  String CAPTCHA_PREFIX = "auth:captcha:";
}
