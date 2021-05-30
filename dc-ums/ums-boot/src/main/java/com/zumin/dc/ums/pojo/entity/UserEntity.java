package com.zumin.dc.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class UserEntity {

  /**
   * 用户ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "用户ID")
  private Long id;

  /**
   * 用户名
   */
  @TableField(value = "username")
  @ApiModelProperty(value = "用户名")
  private String username;

  /**
   * 密码
   */
  @TableField(value = "`password`")
  @ApiModelProperty(value = "密码")
  private String password;

  /**
   * 昵称
   */
  @TableField(value = "nickname")
  @ApiModelProperty(value = "昵称")
  private String nickname;

  /**
   * 头像地址
   */
  @TableField(value = "avatar")
  @ApiModelProperty(value = "头像地址")
  private String avatar;

  /**
   * 创建时间
   */
  @TableField(value = "create_time")
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  /**
   * 最近登录时间
   */
  @TableField(value = "recent_login_time")
  @ApiModelProperty(value = "最近登录时间")
  private LocalDateTime recentLoginTime;

  /**
   * 是否启用
   */
  @TableField(value = "enabled")
  @ApiModelProperty(value = "是否启用")
  private Integer enabled;

  @TableField(exist = false)
  private List<RoleEntity> roleList;
}