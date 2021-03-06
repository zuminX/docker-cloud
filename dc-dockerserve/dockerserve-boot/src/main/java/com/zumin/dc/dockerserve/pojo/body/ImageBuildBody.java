package com.zumin.dc.dockerserve.pojo.body;

import com.zumin.dc.dockerserve.validator.IsVersion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "构建镜像的信息")
public class ImageBuildBody {

  @ApiModelProperty(value = "文件", required = true)
  @NotNull(message = "必须需要构建镜像的文件")
  private MultipartFile file;

  @ApiModelProperty(value = "名称", required = true)
  @NotEmpty(message = "镜像名称不能为空")
  @Length(min = 4, max = 32, message = "镜像名称的字数应在6-32之间")
  private String name;

  @ApiModelProperty(value = "类型", required = true)
  @NotEmpty(message = "文件类型不存在")
  private String type;

  @ApiModelProperty(value = "描述", required = true)
  @Length(max = 256, message = "描述字数不能超过256")
  private String description;

  @ApiModelProperty(value = "版本", required = true)
  @IsVersion
  private String version;

  @ApiModelProperty(value = "是否共享", required = true)
  @NotNull(message = "应用共享状态不能为空")
  private Boolean share;
}
