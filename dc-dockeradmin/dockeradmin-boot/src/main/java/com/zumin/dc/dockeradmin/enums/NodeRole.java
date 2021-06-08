package com.zumin.dc.dockeradmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeRole {

  MANAGER("manager"), WORKER("worker");

  @Getter
  private final String name;
}
