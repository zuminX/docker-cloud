package com.zumin.dc.dockerserve.command.parameter;

import lombok.Builder;
import lombok.Getter;

/**
 * 构建镜像参数
 */
@Getter
@Builder
public class BuildImageParameter {

  private final String name;

  private final String version;

  private final String directory;
}
