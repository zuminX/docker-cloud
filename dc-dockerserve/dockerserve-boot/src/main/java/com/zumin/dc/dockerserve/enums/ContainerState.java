package com.zumin.dc.dockerserve.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 容器状态
 */
@Getter
@AllArgsConstructor
public enum ContainerState {
  CREATED("created"),
  RESTARTING("restarting"),
  RUNNING("running"),
  PAUSED("paused"),
  EXITED("exited"),
  ;

  /**
   * 状态名称
   */
  private final String name;

  /**
   * 判断给定状态字符串是否为运行状态
   *
   * @param name 状态
   * @return 若是运行状态则返回true，否则返回false
   */
  public static boolean isRunning(String name) {
    return RUNNING.name.equalsIgnoreCase(name);
  }
}
