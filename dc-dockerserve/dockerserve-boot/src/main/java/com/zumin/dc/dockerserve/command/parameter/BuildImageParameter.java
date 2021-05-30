package com.zumin.dc.dockerserve.command.parameter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuildImageParameter {

  private final String name;

  private final String version;

  private final String directory;

}
