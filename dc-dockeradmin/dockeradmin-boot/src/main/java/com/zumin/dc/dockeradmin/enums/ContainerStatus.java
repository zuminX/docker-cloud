package com.zumin.dc.dockeradmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 容器状态枚举类
 */
@AllArgsConstructor
public enum ContainerStatus {
  CREATED("created"),
  RESTARTING("restarting"),
  RUNNING("running"),
  PAUSED("paused"),
  EXITED("exited"),
  ;

  @Getter
  private final String name;
}
