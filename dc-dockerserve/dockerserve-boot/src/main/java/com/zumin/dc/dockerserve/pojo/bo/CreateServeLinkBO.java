package com.zumin.dc.dockerserve.pojo.bo;

import lombok.Data;

@Data
public class CreateServeLinkBO {

  /**
   * 被链接服务的ID
   */
  private CreateServeBO beLinkServe;

  /**
   * 被链接服务的别名
   */
  private String alias;
}
