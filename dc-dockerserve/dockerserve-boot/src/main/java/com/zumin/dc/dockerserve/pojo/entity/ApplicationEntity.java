package com.zumin.dc.dockerserve.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用表对象的实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`application`")
public class ApplicationEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 名称
   */
  @TableField(value = "`name`")
  private String name;

  /**
   * 描述
   */
  @TableField(value = "description")
  private String description;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Long userId;

  /**
   * 是否共享
   */
  @TableField(value = "`share`")
  private Boolean share;

  /**
   * 创建时间
   */
  @TableField(value = "create_time")
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  @TableField(value = "update_time")
  private LocalDateTime updateTime;
}