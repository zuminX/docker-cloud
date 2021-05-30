package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import com.github.dockerjava.api.model.Link;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerNetworkVO {

  private ContainerNetworkVO.IpamVO ipamConfig;
  private Link[] links;
  private List<String> aliases;
  private String networkID;
  private String endpointId;
  private String gateway;
  private String ipAddress;
  private Integer ipPrefixLen;
  private String ipV6Gateway;
  private String globalIPv6Address;
  private Integer globalIPv6PrefixLen;
  private String macAddress;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IpamVO {

    private String ipv4Address;
    private String ipv6Address;
  }
}
