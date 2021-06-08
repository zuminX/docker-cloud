package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户拥有的资源的统计表示层类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResourcesStatisticsVO {

  /**
   * 应用总数
   */
  private int applicationTotal;

  /**
   * 镜像总数
   */
  private int imageTotal;

  /**
   * 服务总数
   */
  private int serveTotal;
}
