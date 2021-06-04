package com.zumin.dc.dockerserve.enums;

import com.zumin.dc.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Docker服务系統响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum DockerServeStatusCode implements StatusCode {

  DOCKER_ERROR(10, "服务器在处理Docker时发生了错误"),

  IMAGE_ERROR(20, "服务器在处理镜像时发生了错误"),
  IMAGE_BUILD_ERROR(21, "构建镜像失败"),
  IMAGE_UNAUTHORIZED_ACCESS(22, "没有权限访问该镜像"),

  APPLICATION_ERROR(30, "服务器在处理应用程序时发生了错误"),
  APPLICATION_UNAUTHORIZED_ACCESS(31, "没有权限访问该应用程序"),
  APPLICATION_NOT_START(32, "该服务还未启动"),

  SERVE_ERROR(40, "服务器在处理服务时发生了错误"),
  SERVE_UNAUTHORIZED_ACCESS(41, "没有权限访问该服务"),
  SERVE_CREATE_PORT_ILLEGAL(42, "请求创建非法的端口"),
  SERVE_PATH_ILLEGAL(43, "请求服务路径非法"),
  SERVE_NOT_START(44, "服务未启动"),
  SERVE_ACCESS_PORT_ILLEGAL(45, "请求访问非法的端口"),
  SERVE_NAME_EXISTS(46, "该服务名已被占用，请使用其他名称"),

  SERVE_LINK_ERROR(50, "服务器在处理服务链接时发生了错误"),
  SERVE_LINK_ALIAS_ILLEGAL(51, "服务别名不合法");

  /**
   * 编号
   */
  private final int number;

  /**
   * 错误信息
   */
  private final String message;
}
