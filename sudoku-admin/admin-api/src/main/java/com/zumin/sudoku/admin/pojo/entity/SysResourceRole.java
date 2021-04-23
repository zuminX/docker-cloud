package com.zumin.sudoku.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "com-zumin-sudoku-ums-pojo-entity-SysResourceRole")
@Data
@TableName(value = "sys_resource_role")
public class SysResourceRole {

  /**
   * 资源角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "资源角色ID")
  private Long id;

  /**
   * 资源ID
   */
  @TableField(value = "resource_id")
  @ApiModelProperty(value = "资源ID")
  private Long resourceId;

  /**
   * 角色ID
   */
  @TableField(value = "role_id")
  @ApiModelProperty(value = "角色ID")
  private Long roleId;
}