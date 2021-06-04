package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户拥有的资源的统计表示层类")
public class UserResourcesStatisticsVO {

  @ApiModelProperty(value = "应用总数", required = true)
  private int applicationTotal;

  @ApiModelProperty(value = "镜像总数", required = true)
  private int imageTotal;

  @ApiModelProperty(value = "服务总数", required = true)
  private int serveTotal;
}
