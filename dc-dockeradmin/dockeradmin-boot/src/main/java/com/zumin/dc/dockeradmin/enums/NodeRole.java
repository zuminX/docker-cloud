package com.zumin.dc.dockeradmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点的角色
 */
@AllArgsConstructor
public enum NodeRole {

  /**
   * 管理者
   */
  MANAGER("manager"),
  /**
   * 工作者
   */
  WORKER("worker");

  /**
   * 名称
   */
  @Getter
  private final String name;
}
