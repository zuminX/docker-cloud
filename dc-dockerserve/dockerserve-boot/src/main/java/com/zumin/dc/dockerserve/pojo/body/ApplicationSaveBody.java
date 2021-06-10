package com.zumin.dc.dockerserve.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(description = "保存应用的信息")
public class ApplicationSaveBody {

  @ApiModelProperty("应用ID")
  private Long id;

  @ApiModelProperty(value = "名称", required = true)
  @NotEmpty(message = "应用名称不能为空")
  @Length(min = 6, max = 32, message = "应用名称的字数应在6-32之间")
  private String name;

  @ApiModelProperty(value = "描述", required = true)
  @Length(max = 256, message = "描述字数不能超过256")
  private String description;

  @ApiModelProperty(value = "是否共享", required = true)
  @NotNull(message = "应用共享状态不能为空")
  private Boolean share;

  @ApiModelProperty(value = "服务列表", required = true)
  @NotEmpty(message = "应用至少需要一个服务")
  private List<ServeSaveBody> serveList;
}
