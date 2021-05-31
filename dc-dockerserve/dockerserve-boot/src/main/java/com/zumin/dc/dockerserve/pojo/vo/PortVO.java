package com.zumin.dc.dockerserve.pojo.vo;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortVO {

  /**
   * 容器内端口
   */
  private String innerPort;
  /**
   * 对外暴露端口
   */
  private String exportPort;

  public PortVO(String innerPort) {
    this.innerPort = innerPort;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortVO portVO = (PortVO) o;
    return Objects.equals(innerPort, portVO.innerPort) && Objects.equals(exportPort, portVO.exportPort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(innerPort, exportPort);
  }
}
