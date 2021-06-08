package com.zumin.dc.dockerserve.pojo.vo;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 镜像表示层类
 */
@Data
public class ImageVO {

  /**
   * 镜像ID
   */
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
   * 用户ID
   */
  private Long userId;

  /**
   * 是否共享
   */
  private Boolean share;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;
}
