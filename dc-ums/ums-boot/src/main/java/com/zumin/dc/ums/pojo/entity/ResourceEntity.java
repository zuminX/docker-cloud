package com.zumin.dc.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import lombok.Data;

@Data
@TableName(value = "sys_resource")
public class ResourceEntity {

  /**
   * 资源ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "资源ID")
  private Long id;

  /**
   * 权限标识
   */
  @TableField(value = "perms")
  @ApiModelProperty(value = "权限标识")
  private String perms;

  /**
   * 请求方法类型
   */
  @TableField(value = "method")
  @ApiModelProperty(value = "请求方法类型")
  private String method;

  /**
   * 资源名称
   */
  @TableField(value = "`name`")
  @ApiModelProperty(value = "资源名称")
  private String name;

  // 拥有资源权限角色ID集合
  @TableField(exist = false)
  private Set<Long> roleIds;
}