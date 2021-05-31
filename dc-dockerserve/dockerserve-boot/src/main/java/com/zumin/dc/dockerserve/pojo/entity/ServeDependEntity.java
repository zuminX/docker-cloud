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
 * 服务依赖表对象的实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "serve_depend")
public class ServeDependEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 依赖服务的标识
   */
  @TableField(value = "serve_indicate")
  private String serveIndicate;

  /**
   * 被依赖服务的标识
   */
  @TableField(value = "be_serve_indicate")
  private String beServeIndicate;
}