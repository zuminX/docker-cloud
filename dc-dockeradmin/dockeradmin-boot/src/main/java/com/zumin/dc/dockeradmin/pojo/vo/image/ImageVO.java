package com.zumin.dc.dockeradmin.pojo.vo.image;

import java.util.Map;
import lombok.Data;

@Data
public class ImageVO {

  public Map<String, String> labels;
  private Long created;
  private String id;
  private String parentId;
  private String[] repoTags;
  private String[] repoDigests;
  private Long size;
  private Long virtualSize;
  private Long sharedSize;
  private Integer containers;
}
