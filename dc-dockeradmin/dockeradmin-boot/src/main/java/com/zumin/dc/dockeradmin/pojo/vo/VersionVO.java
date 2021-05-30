package com.zumin.dc.dockeradmin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionVO {

  private String dockerVersion;
  private String apiVersion;
  private String gitCommit;
  private String operatingSystem;
}
