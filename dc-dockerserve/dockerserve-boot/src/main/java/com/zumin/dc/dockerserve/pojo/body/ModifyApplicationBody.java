package com.zumin.dc.dockerserve.pojo.body;

import lombok.Data;

/**
 * 修改应用的信息
 */
@Data
public class ModifyApplicationBody {

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
   * 是否共享
   */
  private Boolean share;
}
