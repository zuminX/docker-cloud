package com.zumin.dc.dockeradmin.enums;

public enum NodeRole {

  MANAGER("manager"), WORKER("worker");

  private final String value;

  NodeRole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
