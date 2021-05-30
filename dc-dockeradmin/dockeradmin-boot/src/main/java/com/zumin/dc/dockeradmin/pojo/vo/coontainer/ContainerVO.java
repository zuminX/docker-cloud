package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerVO {

  public ContainerPortVO[] ports;
  public Map<String, String> labels;
  private String command;
  private Long created;
  private String id;
  private String image;
  private String imageId;
  private String[] names;
  private String status;
  private String state;
  private Long sizeRw;
  private Long sizeRootFs;
  private String hostConfig;
  private Map<String, ContainerNetworkVO> networkSettings;
  private List<ContainerMountVO> mounts;
}
