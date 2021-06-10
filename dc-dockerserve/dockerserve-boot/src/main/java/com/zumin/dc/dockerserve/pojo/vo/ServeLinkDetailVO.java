package com.zumin.dc.dockerserve.pojo.vo;

import lombok.Data;

/**
 * 服务链接详情表示层类
 */
@Data
public class ServeLinkDetailVO {

  /**
   * 被链接服务的ID
   */
  private Long beLinkServeId;

  /**
   * 被链接服务的名称
   */
  private String beLinkServeName;

  /**
   * 被链接服务的名称
   */
  private String name;
}
