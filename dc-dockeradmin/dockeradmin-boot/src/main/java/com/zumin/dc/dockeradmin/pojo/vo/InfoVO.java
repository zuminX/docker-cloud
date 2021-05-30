package com.zumin.dc.dockeradmin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoVO {

  private String name;
  private String storageDriver;
  private String kernelVersion;
  private String serverVersion;
  private String dockerRootDir;
  private Integer cpus;
  private Long memory;
}
