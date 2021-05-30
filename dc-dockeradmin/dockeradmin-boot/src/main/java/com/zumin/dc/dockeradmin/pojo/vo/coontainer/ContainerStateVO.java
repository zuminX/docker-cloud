package com.zumin.dc.dockeradmin.pojo.vo.coontainer;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerStateVO {

  private String status;
  private Boolean running;
  private Boolean paused;
  private Boolean restarting;
  private Boolean oomKilled;
  private Boolean dead;
  private Long pid;
  private Long exitCode;
  private String error;
  private String startedAt;
  private String finishedAt;
  private HealthStateVO health;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HealthStateVO {

    private String status;
    private Integer failingStreak;
    private List<HealthStateLogVO> log;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HealthStateLogVO {

    private String start;
    private String end;
    private Long exitCode;
    private String output;
  }
}
