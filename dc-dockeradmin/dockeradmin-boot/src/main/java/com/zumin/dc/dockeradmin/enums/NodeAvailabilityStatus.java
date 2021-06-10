package com.zumin.dc.dockeradmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点的可用状态
 */
@AllArgsConstructor
public enum NodeAvailabilityStatus {

  /**
   * 活跃
   */
  ACTIVE("active"),
  /**
   * 清空
   */
  DRAIN("drain");

  /**
   * 名称
   */
  @Getter
  private final String name;
}
