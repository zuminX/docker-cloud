package com.zumin.dc.dockerserve.pojo.vo;

import java.util.List;
import lombok.Data;

/**
 * 服务详情表示层类
 */
@Data
public class ServeDetailVO {

  /**
   * 镜像ID
   */
  private Integer imageId;

  /**
   * 镜像名称
   */
  private String imageName;

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
  private List<ServeLinkDetailVO> linkServeList;
}
