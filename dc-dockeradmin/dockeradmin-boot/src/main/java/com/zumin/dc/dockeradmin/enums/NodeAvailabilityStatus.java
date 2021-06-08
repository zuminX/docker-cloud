package com.zumin.dc.dockeradmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeAvailabilityStatus {

  ACTIVE("active"), DRAIN("drain");

  @Getter
  private final String name;
}
