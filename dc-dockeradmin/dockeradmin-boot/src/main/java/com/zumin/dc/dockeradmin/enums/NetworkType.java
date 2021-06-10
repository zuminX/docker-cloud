package com.zumin.dc.dockeradmin.enums;

import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Docker网络类型
 */
@AllArgsConstructor
public enum NetworkType {
  /**
   * 内建
   */
  BUILTIN("builtin"),
  /**
   * 自定义
   */
  CUSTOM("custom");

  /**
   * 名称
   */
  @Getter
  private final String name;

  /**
   * 获取该类型的名称，并将其包装为集合
   *
   * @return 名称集合
   */
  public Collection<String> getNameAsArray() {
    return Collections.singleton(name);
  }
}
