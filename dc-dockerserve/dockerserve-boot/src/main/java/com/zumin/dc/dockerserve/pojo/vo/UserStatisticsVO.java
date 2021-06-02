package com.zumin.dc.dockerserve.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsVO {

  private int applicationTotal;
  private int imageTotal;
  private int serveTotal;
}
