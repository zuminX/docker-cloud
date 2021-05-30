package com.zumin.dc.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "role")
public class RoleEntity {

  /**
   * 角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "角色ID")
  private Long id;

  /**
   * 角色名
   */
  @TableField(value = "`name`")
  @ApiModelProperty(value = "角色名")
  private String name;

  /**
   * 角色昵称
   */
  @TableField(value = "nickname")
  @ApiModelProperty(value = "角色昵称")
  private String nickname;
}