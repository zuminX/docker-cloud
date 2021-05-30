package com.zumin.dc.dockeradmin.enums;

import java.util.Collection;
import java.util.Collections;

public enum ContainerStatus {
  CREATED("created"),
  RESTARTING("restarting"),
  RUNNING("running"),
  PAUSED("paused"),
  EXITED("exited"),
  ;

  private final String name;

  ContainerStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Collection<String> getNameAsArray() {
    return Collections.singleton(name);
  }
}
