package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 镜像名称表示层类
 */
@Data
public class ImageNameVO {

  /**
   * 镜像ID
   */
  private Long id;

  /**
   * 镜像名称
   */
  private String name;
}
