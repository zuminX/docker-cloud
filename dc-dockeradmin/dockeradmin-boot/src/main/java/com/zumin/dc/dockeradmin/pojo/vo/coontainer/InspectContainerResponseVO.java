package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.VolumeBind;
import com.github.dockerjava.api.model.VolumeRW;
import com.zumin.dc.dockeradmin.pojo.vo.HostConfigVO;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerConfigVO.ExposedPortVO;
import com.zumin.dc.dockeradmin.pojo.vo.image.InspectImageResponseVO.GraphDriverVO;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectContainerResponseVO {

  private String[] args;
  private ContainerConfigVO config;
  private String created;
  private String driver;
  private String execDriver;
  private HostConfigVO hostConfig;
  private String hostnamePath;
  private String hostsPath;
  private String logPath;
  private String id;
  private Integer sizeRootFs;
  private String imageId;
  private String mountLabel;
  private String name;
  private Integer restartCount;
  private NetworkSettingsVO networkSettings;
  private String path;
  private String processLabel;
  private String resolvConfPath;
  private List<String> execIds;
  private ContainerStateVO state;
  private VolumeBind[] volumes;
  private VolumeRW[] volumesRW;
  private NodeVO node;
  private List<MountVO> mounts;
  private GraphDriverVO graphDriver;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NetworkSettingsVO {

    private String bridge;
    private String sandboxId;
    private Boolean hairpinMode;
    private String linkLocalIPv6Address;
    private Integer linkLocalIPv6PrefixLen;
    private Map<ExposedPortVO, Binding[]> ports;
    private String sandboxKey;
    private Object secondaryIPAddresses;
    private Object secondaryIPv6Addresses;
    private String endpointID;
    private String gateway;
    private Map<String, Map<String, String>> portMapping;
    private String globalIPv6Address;
    private Integer globalIPv6PrefixLen;
    private String ipAddress;
    private Integer ipPrefixLen;
    private String ipV6Gateway;
    private String macAddress;
    private Map<String, ContainerNetworkVO> networks;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NodeVO {

    private String id;
    private String ip;
    private String addr;
    private String name;
    private Integer cpus;
    private Long memory;
    private Map<String, String> labels;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MountVO {

    private String type;
    private String source;
    private String target;
    private Boolean readOnly;
    private String bindOptions;
    private VolumeOptionsVO volumeOptions;
    private TmpfsOptionsVO tmpfsOptions;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class VolumeOptionsVO {

    private Boolean noCopy;
    private Map<String, String> labels;
    private DriverVO driverConfig;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DriverVO {

    private String name;
    private Map<String, String> options;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TmpfsOptionsVO {

    private Long sizeBytes;
    private Integer mode;
  }
}
