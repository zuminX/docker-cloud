package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.zumin.dc.dockeradmin.pojo.vo.VolumeStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker卷的服务层类
 */
@Service
@RequiredArgsConstructor
public class VolumeService {

  private final DockerClient dockerClient;

  /**
   * 列出所有的卷
   *
   * @return 卷对象列表
   */
  public List<InspectVolumeResponse> list() {
    return dockerClient.listVolumesCmd().exec().getVolumes();
  }

  /**
   * 删除指定的卷
   *
   * @param name 卷的名称
   */
  public void remove(String name) {
    dockerClient.removeVolumeCmd(name).exec();
  }

  /**
   * 获取卷的统计信息
   *
   * @return 卷的统计信息
   */
  public VolumeStatsVO getStatistics() {
    List<InspectVolumeResponse> volume = list();
    //TODO 待完成
    return new VolumeStatsVO(volume.size(), 0);
  }
}
