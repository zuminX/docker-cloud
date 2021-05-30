package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerConfigVO {

  private Boolean attachStderr;
  private Boolean attachStdin;
  private Boolean attachStdout;
  private String[] cmd;
  private String domainName;
  private String[] entrypoint;
  private String[] env;
  private ExposedPortVO[] exposedPorts;
  private String hostName;
  private String image;
  private Map<String, String> labels;
  private String macAddress;
  private Boolean networkDisabled;
  private String[] onBuild;
  private Boolean stdinOpen;
  private String[] portSpecs;
  private Boolean stdInOnce;
  private Boolean tty;
  private String user;
  private Map<String, ?> volumes;
  private String workingDir;
  private HealthCheckVO healthCheck;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HealthCheckVO {

    private Long interval;
    private Long timeout;
    private List<String> test;
    private Integer retries;
    private Long startPeriod;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ExposedPortVO {

    private String protocol;
    private int port;
  }
}
