package com.zumin.dc.dockeradmin.pojo.vo;

import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.VolumesFrom;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerConfigVO.ExposedPortVO;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.InspectContainerResponseVO.MountVO;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostConfigVO {

  private BindVO[] binds;
  private Integer blkioWeight;
  private List<BlkioWeightDeviceVO> blkioWeightDevice;
  private List<BlkioRateDeviceVO> blkioDeviceReadBps;
  private List<BlkioRateDeviceVO> blkioDeviceWriteBps;
  private List<BlkioRateDeviceVO> blkioDeviceReadIOps;
  private List<BlkioRateDeviceVO> blkioDeviceWriteIOps;
  private Long memorySwappiness;
  private Long nanoCPUs;
  private String[] capAdd;
  private String[] capDrop;
  private String containerIDFile;
  private Long cpuPeriod;
  private Long cpuRealtimePeriod;
  private Long cpuRealtimeRuntime;
  private Integer cpuShares;
  private Long cpuQuota;
  private String cpusetCpus;
  private String cpusetMems;
  private DeviceVO[] devices;
  private List<String> deviceCgroupRules;
  private List<DeviceRequestVO> deviceRequests;
  private Long diskQuota;
  private String[] dns;
  private List<String> dnsOptions;
  private String[] dnsSearch;
  private String[] extraHosts;
  private List<String> groupAdd;
  private String ipcMode;
  private String cgroup;
  private Link[] links;
  private LogConfigVO logConfig;
  private LxcConfVO[] lxcConf;
  private Long memory;
  private Long memorySwap;
  private Long memoryReservation;
  private Long kernelMemory;
  private String networkMode;
  private Boolean oomKillDisable;
  private Boolean init;
  private Boolean autoRemove;
  private Integer oomScoreAdj;
  private Map<ExposedPortVO, Binding[]> portBindings;
  private Boolean privileged;
  private Boolean publishAllPorts;
  private Boolean readonlyRootfs;
  private RestartPolicyVO restartPolicy;
  private UlimitVO[] ulimits;
  private Long cpuCount;
  private Long cpuPercent;
  private Long ioMaximumIOps;
  private Long ioMaximumBandwidth;
  private VolumesFrom[] volumesFrom;
  private List<MountVO> mounts;
  private String pidMode;
  private String isolation;
  private List<String> securityOpts;
  private Map<String, String> storageOpt;
  private String cgroupParent;
  private String volumeDriver;
  private Long shmSize;
  private Long pidsLimit;
  private String runtime;
  private Map<String, String> tmpFs;
  private String utSMode;
  private String usernsMode;
  private Map<String, String> sysctls;
  private List<Integer> consoleSize;


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BindVO {

    private String path;
    private String volume;
    private String accessMode;
    private Boolean noCopy;
    private String secMode;
    private String propagationMode;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BlkioWeightDeviceVO {

    private String path;
    private Integer weight;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BlkioRateDeviceVO {

    private String path;
    private Long rate;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DeviceVO {

    private String cGroupPermissions = "";
    private String pathOnHost = null;
    private String pathInContainer = null;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DeviceRequestVO {

    private String driver;
    private Integer count;
    private List<String> deviceIds;
    private List<List<String>> capabilities;
    private Map<String, String> options;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LogConfigVO {

    public String type = null;
    public Map<String, String> config;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LxcConfVO {

    public String key;
    public String value;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RestartPolicyVO {

    private Integer maximumRetryCount = 0;
    private String name = "";
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UlimitVO {

    private String name;
    private Long soft;
    private Long hard;
  }
}