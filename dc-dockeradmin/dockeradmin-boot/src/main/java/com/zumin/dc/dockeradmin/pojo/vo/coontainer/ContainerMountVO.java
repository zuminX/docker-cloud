package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerMountVO {

  private String name;
  private String source;
  private String destination;
  private String driver;
  private String mode;
  private boolean rw;
  private String propagation;
}
