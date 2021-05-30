package com.zumin.dc.dockeradmin.pojo.vo;

import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerStatsVO;
import com.zumin.dc.dockeradmin.pojo.vo.image.ImageStatsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardStatsVO {

  private ContainerStatsVO containerStats;
  private ImageStatsVO imageStats;
  private NetworkStatsVO networkStats;
  private VolumeStatsVO volumeStats;
  private VersionVO version;
  private InfoVO info;
}
