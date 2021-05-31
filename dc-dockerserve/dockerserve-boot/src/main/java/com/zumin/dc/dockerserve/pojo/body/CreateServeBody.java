package com.zumin.dc.dockerserve.pojo.body;

import java.util.List;
import lombok.Data;

/**
 * 创建服务的信息
 */
@Data
public class CreateServeBody {

  /**
   * 镜像ID
   */
  private Integer imageId;

  /**
   * 是否共享
   */
  private Boolean share;

  /**
   * 描述
   */
  private String description;

  /**
   * 服务名称
   */
  private String name;

  /**
   * 内部端口
   */
  private List<Integer> portList;

  /**
   * 链接的服务
   */
  private List<CreateServeLinkBody> linkServeList;
}
