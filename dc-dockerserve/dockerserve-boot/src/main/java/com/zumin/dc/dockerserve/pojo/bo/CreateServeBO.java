package com.zumin.dc.dockerserve.pojo.bo;

import java.util.List;
import lombok.Data;

@Data
public class CreateServeBO {

  /**
   * 镜像ID
   */
  private String imageId;

  /**
   * 服务名称
   */
  private String name;

  /**
   * 内部端口
   */
  private List<String> portList;

  /**
   * 链接的服务
   */
  private List<CreateServeLinkBO> linkServeList;
}
