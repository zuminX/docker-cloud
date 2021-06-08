package com.zumin.dc.dockerserve.pojo.vo;

import java.util.List;
import lombok.Data;

/**
 * 应用详情表示层类
 */
@Data
public class ApplicationDetailVO {

  /**
   * 名称
   */
  private String name;

  /**
   * 描述
   */
  private String description;

  /**
   * 是否共享
   */
  private Boolean share;

  /**
   * 服务列表
   */
  private List<ServeDetailVO> serveList;
}