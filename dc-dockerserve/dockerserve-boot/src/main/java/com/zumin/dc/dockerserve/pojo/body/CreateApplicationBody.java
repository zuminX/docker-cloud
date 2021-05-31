package com.zumin.dc.dockerserve.pojo.body;

import java.util.List;
import lombok.Data;

/**
 * 创建应用的信息
 */
@Data
public class CreateApplicationBody {

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
  private List<CreateServeBody> serveList;
}
