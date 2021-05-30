package com.zumin.dc.ums.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("注册用户信息体类")
public class RegisterUserBody implements Serializable {

  private static final long serialVersionUID = 7615402214779318417L;

  @ApiModelProperty("用户名")
  @NotEmpty(message = "用户名不能为空")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "用户名只能是数字和字母的组合")
  private String username;

  @ApiModelProperty("密码")
  @NotEmpty(message = "密码不能为空")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "密码只能是数字和字母的组合")
  private String password;

  @ApiModelProperty("用户昵称")
  @NotEmpty(message = "昵称不能为空")
  @Length(min = 4, max = 16, message = "昵称的长度为4-16位")
  private String nickname;

  @ApiModelProperty("验证码")
  @NotEmpty(message = "验证码不能为空")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "验证码只能是数字和字母的组合")
  private String code;

  @ApiModelProperty("唯一标识")
  @NotEmpty(message = "唯一标识不能为空")
  private String uuid;
}