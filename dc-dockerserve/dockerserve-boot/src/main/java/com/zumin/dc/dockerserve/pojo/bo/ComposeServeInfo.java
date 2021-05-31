package com.zumin.dc.dockerserve.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Compose服务信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComposeServeInfo {

  /**
   * 服务标识
   */
  private String serveIndicate;

  /**
   * 容器ID
   */
  private String containerId;
}
