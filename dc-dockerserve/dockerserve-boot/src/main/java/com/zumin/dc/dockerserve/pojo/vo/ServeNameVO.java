package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "服务名称表示层类")
public class ServeNameVO {

  @ApiModelProperty(value = "服务ID", required = true)
  private Long id;

  @ApiModelProperty(value = "服务名称", required = true)
  private String name;
}
