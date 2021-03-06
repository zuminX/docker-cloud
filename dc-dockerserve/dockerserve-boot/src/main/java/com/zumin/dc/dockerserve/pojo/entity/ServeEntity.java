package com.zumin.dc.dockerserve.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务表对象的实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "serve")
public class ServeEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 名称
   */
  @TableField(value = "`name`")
  private String name;

  /**
   * 链接名称
   */
  @TableField(value = "`link_name`")
  private String linkName;

  /**
   * 描述
   */
  @TableField(value = "description")
  private String description;

  /**
   * 是否共享
   */
  @TableField(value = "`share`")
  private Boolean share;

  /**
   * 服务标识
   */
  @TableField(value = "serve_indicate")
  private String serveIndicate;

  /**
   * 镜像标识
   */
  @TableField(value = "image_indicate")
  private String imageIndicate;

  /**
   * 环境信息（以分号分割）
   */
  @TableField(value = "environment")
  private String environment;

  /**
   * 端口信息（以分号分割）
   */
  @TableField(value = "port")
  private String port;

  /**
   * 挂载目录（以分号分割）
   */
  @TableField(value = "volume")
  private String volume;

  /**
   * 应用ID
   */
  @TableField(value = "application_id")
  private Long applicationId;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Long userId;
}