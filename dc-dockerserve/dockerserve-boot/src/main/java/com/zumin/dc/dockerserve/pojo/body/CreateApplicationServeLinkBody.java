package com.zumin.dc.dockerserve.pojo.body;

import lombok.Data;

@Data
public class CreateApplicationServeLinkBody {

  /**
   * 被链接服务的ID
   */
  private Long beLinkServeId;

  /**
   * 被链接服务的别名
   */
  private String alias;
}
