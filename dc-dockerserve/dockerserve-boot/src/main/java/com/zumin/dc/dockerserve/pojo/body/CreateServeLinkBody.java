package com.zumin.dc.dockerserve.pojo.body;

import lombok.Data;

/**
 * 创建服务链接的信息
 */
@Data
public class CreateServeLinkBody {

  /**
   * 被链接服务的ID
   */
  private Long beLinkServeId;

  /**
   * 被链接服务的别名
   */
  private String alias;
}
