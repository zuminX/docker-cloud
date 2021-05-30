package com.zumin.dc.dockerserve.pojo.body;

import lombok.Data;

@Data
public class ModifyImageBody {

  private Long id;
  /**
   * 名称
   */
  private String name;
  /**
   * 描述
   */
  private String description;

  /**
   * 版本
   */
  private String version;

  /**
   * 是否共享
   */
  private Boolean share;
}
