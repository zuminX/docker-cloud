package com.zumin.dc.dockeradmin.pojo.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigVO {

  private String id;
  private Date createdAt;
  private Date updatedAt;
  private String spec;
  private Long version;
}
