package com.zumin.dc.auth.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Oauth2获取Token返回信息封装
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class OAuth2TokenDTO {

  @ApiModelProperty("访问令牌")
  private String token;
  @ApiModelProperty("刷令牌")
  private String refreshToken;
  @ApiModelProperty("有效时间（秒）")
  private int expiresIn;
}
