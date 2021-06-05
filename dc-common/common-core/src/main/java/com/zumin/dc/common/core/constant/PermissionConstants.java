package com.zumin.dc.common.core.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 权限常量类
 */
public interface PermissionConstants {

  /**
   * 管理员名
   */
  String ADMIN_NAME = "ROLE_ADMIN";

  /**
   * 用户名
   */
  String USER_NAME = "ROLE_USER";

  /**
   * 用户角色名
   */
  List<String> USER_ROLE_NAME = Collections.singletonList(USER_NAME);

  /**
   * 管理员角色名
   */
  List<String> ADMIN_ROLE_NAME = Arrays.asList(ADMIN_NAME, USER_NAME);

  /**
   * 管理员角色ID
   */
  Long ADMIN_ROLE_ID = 1L;
}