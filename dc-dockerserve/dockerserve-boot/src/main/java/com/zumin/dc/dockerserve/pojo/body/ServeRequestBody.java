package com.zumin.dc.dockerserve.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "请求服务的信息")
public class ServeRequestBody {

  @ApiModelProperty(value = "服务ID", required = true)
  private Long serveId;

  @ApiModelProperty(value = "访问端口号", required = true)
  private Integer port;

  /**
   * 获取请求服务的路径表示
   *
   * @return 路径字符串
   */
  public String toPath() {
    return "/" + serveId + ":" + port;
  }
}
