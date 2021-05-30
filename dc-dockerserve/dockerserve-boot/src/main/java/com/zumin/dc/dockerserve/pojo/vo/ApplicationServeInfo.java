package com.zumin.dc.dockerserve.pojo.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationServeInfo {

  private String name;
  private String state;
  private String description;
  private List<PortVO> ports;
}
