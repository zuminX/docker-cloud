package com.zumin.dc.auth.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("验证码显示类")
public class CaptchaVO implements Serializable {

  private static final long serialVersionUID = 8718519598958140442L;

  @ApiModelProperty("唯一标识")
  private String uuid;

  @ApiModelProperty("验证码图片Base64编码")
  private String captchaBase64;
}
