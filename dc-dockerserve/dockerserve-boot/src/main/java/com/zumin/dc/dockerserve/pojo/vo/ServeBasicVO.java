package com.zumin.dc.dockerserve.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务基本信息表示层类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServeBasicVO {

  /**
   * 服务名称
   */
  private String name;

  /**
   * 服务状态
   */
  private String state;
}
