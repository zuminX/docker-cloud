package com.zumin.dc.dockerserve.pojo.bo;

import java.util.List;
import lombok.Data;

@Data
public class CreateApplicationBO {

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
  private List<CreateServeBO> serveList;
}
