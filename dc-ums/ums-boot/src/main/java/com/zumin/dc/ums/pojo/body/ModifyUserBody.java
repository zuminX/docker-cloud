package com.zumin.dc.ums.pojo.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("修改用户信息体类")
public class ModifyUserBody implements Serializable {

  private static final long serialVersionUID = -2912099437520184948L;

  @ApiModelProperty("用户ID")
  @NotNull(message = "用户ID不能为空")
  @PositiveOrZero(message = "用户ID不能为负数")
  private Long id;

  @ApiModelProperty("用户名")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  private String username;

  @ApiModelProperty("昵称")
  @Length(min = 4, max = 32, message = "昵称的长度为4-32位")
  private String nickname;

  @ApiModelProperty("创建时间")
  @NotNull(message = "创建时间不能为空")
  @Past(message = "创建时间必须是过去的时间")
  private LocalDateTime createTime;

  @ApiModelProperty("最近登录时间")
  @NotNull(message = "最近登录时间不能为空")
  @Past(message = "最近登录时间必须是过去的时间")
  private LocalDateTime recentLoginTime;

  @ApiModelProperty("是否启用")
  @NotNull(message = "是否启用不能为空")
  private Boolean enabled;

  @ApiModelProperty("角色名列表")
  @NotEmpty(message = "用户至少要有一个角色")
  private List<String> roleNameList;
}
