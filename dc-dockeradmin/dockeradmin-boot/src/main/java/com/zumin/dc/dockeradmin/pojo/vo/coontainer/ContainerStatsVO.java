package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerStatsVO {

  private long total;
  private long createdTotal;
  private long runningTotal;
  private long pausedTotal;
  private long restartingTotal;
  private long exitedTotal;
}
