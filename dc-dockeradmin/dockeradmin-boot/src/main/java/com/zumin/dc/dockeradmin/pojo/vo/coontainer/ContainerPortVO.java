package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerPortVO {

  private String ip;
  private Integer privatePort;
  private Integer publicPort;
  private String type;
}
