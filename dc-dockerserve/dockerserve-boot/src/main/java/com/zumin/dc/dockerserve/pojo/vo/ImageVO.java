package com.zumin.dc.dockerserve.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@ApiModel(description = "镜像表示层类")
public class ImageVO {

  @ApiModelProperty(value = "镜像ID", required = true)
  private Long id;

  @ApiModelProperty(value = "名称", required = true)
  private String name;

  @ApiModelProperty(value = "描述", required = true)
  private String description;

  @ApiModelProperty(value = "版本", required = true)
  private String version;

  @ApiModelProperty(value = "用户ID", required = true)
  private Long userId;

  @ApiModelProperty(value = "是否共享", required = true)
  private Boolean share;

  @ApiModelProperty(value = "创建时间", required = true)
  private LocalDateTime createTime;
}
