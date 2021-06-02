package com.zumin.dc.dockerserve.enums;

import java.util.Arrays;

public enum ContainerState {
  CREATED("created"),
  RESTARTING("restarting"),
  RUNNING("running"),
  PAUSED("paused"),
  EXITED("exited"),
  ;

  private final String name;

  ContainerState(String name) {
    this.name = name;
  }

  public static boolean isRunning(String name) {
    return RUNNING.name.equalsIgnoreCase(name);
  }

  public static ContainerState findByName(String name) {
    return Arrays.stream(values()).filter(status -> status.name.equals(name)).findFirst().orElse(null);
  }

  public String getName() {
    return name;
  }
}
