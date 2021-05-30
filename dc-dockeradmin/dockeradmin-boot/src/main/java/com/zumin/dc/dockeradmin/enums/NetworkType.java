package com.zumin.dc.dockeradmin.enums;

import java.util.Collection;
import java.util.Collections;

public enum NetworkType {
  BUILTIN("builtin"),
  CUSTOM("custom");

  private final String name;

  NetworkType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Collection<String> getNameAsArray() {
    return Collections.singleton(name);
  }
}
