package com.zumin.dc.dockerserve.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortVO {

  /**
   * 容器内端口
   */
  private String innerPort;
  /**
   * 对外暴露端口
   */
  private String exportPort;
}
