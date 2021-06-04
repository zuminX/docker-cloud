package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "镜像名称表示层类")
public class ImageNameVO {

  @ApiModelProperty(value = "镜像ID", required = true)
  private Long id;

  @ApiModelProperty(value = "镜像名称", required = true)
  private String name;
}
