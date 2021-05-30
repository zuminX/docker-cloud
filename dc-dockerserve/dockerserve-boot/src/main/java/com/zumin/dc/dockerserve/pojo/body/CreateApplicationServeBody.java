package com.zumin.dc.dockerserve.pojo.body;

import java.util.List;
import lombok.Data;

@Data
public class CreateApplicationServeBody {

  /**
   * 镜像ID
   */
  private Integer imageId;

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
  private List<CreateApplicationServeLinkBody> linkServeList;
}
