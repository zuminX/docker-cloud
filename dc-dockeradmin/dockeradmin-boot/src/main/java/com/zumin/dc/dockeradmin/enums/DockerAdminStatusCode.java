package com.zumin.dc.dockeradmin.enums;

import com.zumin.dc.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Docker管理系統响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum DockerAdminStatusCode implements StatusCode {

  DOCKER_ERROR(10, "服务器在处理Docker时发生了错误"),
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
