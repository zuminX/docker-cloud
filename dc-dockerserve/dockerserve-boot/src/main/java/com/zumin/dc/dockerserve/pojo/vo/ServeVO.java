package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "服务表示层类")
public class ServeVO {

  @ApiModelProperty(value = "服务ID", required = true)
  private Long id;

  @ApiModelProperty(value = "名称", required = true)
  private String name;

  @ApiModelProperty(value = "状态", required = true)
  private String state;

  @ApiModelProperty(value = "描述", required = true)
  private String description;

  @ApiModelProperty(value = "用户ID", required = true)
  private Long userId;

  @ApiModelProperty(value = "内部端口号", required = true)
  private Set<Integer> portList;
}
