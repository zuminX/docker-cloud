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
 * 服务链接表对象的实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "serve_link")
public class ServeLinkEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 链接服务的标识
   */
  @TableField(value = "serve_indicate")
  private String serveIndicate;

  /**
   * 被链接服务的标识
   */
  @TableField(value = "be_serve_indicate")
  private String beServeIndicate;

  /**
   * 被链接服务的名称
   */
  @TableField(value = "`name`")
  private String name;
}