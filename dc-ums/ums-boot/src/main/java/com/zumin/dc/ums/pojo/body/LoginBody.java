package com.zumin.dc.ums.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录用户信息体类")
public class LoginBody implements Serializable {

  private static final long serialVersionUID = 9125412007527553474L;

  @ApiModelProperty(value = "用户名", required = true)
  @NotEmpty(message = "用户名不能为空")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "用户名只能是数字和字母的组合")
  private String username;

  @ApiModelProperty(value = "密码", required = true)
  @NotEmpty(message = "密码不能为空")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "密码只能是数字和字母的组合")
  private String password;

  @ApiModelProperty(value = "验证码", required = true)
  @NotEmpty(message = "验证码不能为空")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "验证码只能是数字和字母的组合")
  private String code;

  @ApiModelProperty(value = "唯一标识", required = true)
  @NotEmpty(message = "唯一标识不能为空")
  private String uuid;
}
