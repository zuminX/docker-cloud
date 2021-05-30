package com.zumin.dc.dockerserve.pojo.body;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BuildImageBody {

  /**
   * 文件
   */
  private MultipartFile file;
  /**
   * 名称
   */
  private String name;
  /**
   * 类型
   */
  private String type;
  /**
   * 描述
   */
  private String description;

  /**
   * 版本
   */
  private String version;

  /**
   * 是否共享
   */
  private Boolean share;
}
