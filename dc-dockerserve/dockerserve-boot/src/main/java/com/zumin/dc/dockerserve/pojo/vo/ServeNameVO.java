package com.zumin.dc.dockerserve.pojo.vo;

import lombok.Data;

/**
 * 服务名称表示层类
 */
@Data
public class ServeNameVO {

  /**
   * 服务ID
   */
  private Long id;

  /**
   * 服务名称
   */
  private String name;
}
