package com.zumin.dc.dockerserve.pojo.vo;

import java.util.Set;
import lombok.Data;

/**
 * 服务表示层类
 */
@Data
public class ServeVO {

  /**
   * 服务ID
   */
  private Long id;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private String state;

  /**
   * 描述
   */
  private String description;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 内部端口号
   */
  private Set<Integer> portList;
}
