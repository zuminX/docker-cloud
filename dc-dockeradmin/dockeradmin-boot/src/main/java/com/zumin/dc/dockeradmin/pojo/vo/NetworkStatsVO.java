package com.zumin.dc.dockeradmin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetworkStatsVO {

  private int total;
  private int buildInTotal;
  private int customTotal;
}
