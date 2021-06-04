package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "服务基本信息表示层类")
public class ServeBasicVO {

  @ApiModelProperty(value = "服务名称", required = true)
  private String name;

  @ApiModelProperty(value = "服务状态", required = true)
  private String state;
}
