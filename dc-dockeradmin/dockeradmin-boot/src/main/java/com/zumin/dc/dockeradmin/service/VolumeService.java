package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.zumin.dc.dockeradmin.pojo.vo.VolumeStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolumeService {

  private final DockerClient dockerClient;

  public List<InspectVolumeResponse> listOfAllVolumes() {
    return dockerClient.listVolumesCmd().exec().getVolumes();
  }

  public void removeVolume(String volumeName) {
    dockerClient.removeVolumeCmd(volumeName).exec();
  }

  public VolumeStatsVO getStatistics() {
    List<InspectVolumeResponse> volume = listOfAllVolumes();
    //TODO 待完成
    return new VolumeStatsVO(volume.size(), 0);
  }
}
