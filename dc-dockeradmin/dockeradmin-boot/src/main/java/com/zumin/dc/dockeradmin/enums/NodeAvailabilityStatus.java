package com.zumin.dc.dockeradmin.enums;

public enum NodeAvailabilityStatus {

  ACTIVE("active"), DRAIN("drain");

  private final String value;

  NodeAvailabilityStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
