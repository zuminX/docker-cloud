package com.zumin.dc.dockerserve.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(description = "保存服务链接的信息")
public class ServeLinkSaveBody {

  @ApiModelProperty(value = "被链接服务的ID", required = true)
  @NotNull(message = "未指定服务的链接服务")
  private Long beLinkServeId;

  @ApiModelProperty(value = "被链接服务的名称", required = true)
  @Length(max = 16, message = "镜像名称的字数最多为16")
  private String name;
}
