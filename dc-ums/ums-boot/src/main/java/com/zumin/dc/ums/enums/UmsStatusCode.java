package com.zumin.dc.ums.enums;


import com.zumin.dc.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 用户系統响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum UmsStatusCode implements StatusCode {

  USER_ERROR(10, "服务器在处理用户时发生了错误"),
  USER_NOT_FOUND(11, "未找到指定的用户"),
  USER_HAS_EQUAL_NAME(12, "存在相同用户名的用户"),
  USER_NOT_AUTHORITY(13, "没有权限访问该地址"),
  USER_REPEAT_PASSWORD_ERROR(14, "重复密码与密码不一致"),
  USER_NOT_ENABLED(15, "该用户处于禁用状态"),
  USER_NOT_MODIFY_AUTHORITY(16, "没有修改的权限"),
  USER_NOT_LOGIN(17, "用户未登陆"),
  ;

  /**
   * 编号
   */
  private final int number;

  /**
   * 错误信息
   */
  private final String message;
}
