package com.zumin.dc.dockerserve.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(description = "创建服务的信息")
public class CreateServeBody {

  @ApiModelProperty(value = "镜像ID", required = true)
  @NotNull(message = "未指定服务的镜像")
  private Integer imageId;

  @ApiModelProperty(value = "是否共享", required = true)
  @NotNull(message = "应用共享状态不能为空")
  private Boolean share;

  @ApiModelProperty(value = "描述", required = true)
  @Length(max = 256, message = "描述字数不能超过256")
  private String description;

  @ApiModelProperty(value = "服务名称", required = true)
  @NotEmpty(message = "镜像名称不能为空")
  @Length(min = 4, max = 32, message = "镜像名称的字数应在6-32之间")
  private String name;

  @ApiModelProperty(value = "内部端口", required = true)
  @NotEmpty(message = "内部端口不能为空")
  private List<Integer> portList;

  @ApiModelProperty(value = "链接的服务", required = true)
  private List<CreateServeLinkBody> linkServeList;
}
