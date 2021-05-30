package com.zumin.dc.dockeradmin.pojo.vo.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageStatsVO {

  private int total;
  private int danglingTotal;
}
