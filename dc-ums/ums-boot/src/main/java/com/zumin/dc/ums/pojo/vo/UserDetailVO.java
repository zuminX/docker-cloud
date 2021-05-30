package com.zumin.dc.ums.pojo.vo;


import com.zumin.dc.ums.pojo.entity.RoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户详细信息显示层类")
public class UserDetailVO implements Serializable {

  private static final long serialVersionUID = 3927503255291517116L;

  @ApiModelProperty("用户ID")
  private Long id;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("昵称")
  private String nickname;

  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty("最近登录时间")
  private LocalDateTime recentLoginTime;

  @ApiModelProperty("是否启用")
  private Integer enabled;

  @ApiModelProperty("角色列表")
  private List<RoleEntity> roleList;
}