package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Version;
import com.zumin.dc.dockeradmin.convert.VersionConvert;
import com.zumin.dc.dockeradmin.pojo.vo.VersionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {

  private final DockerClient dockerClient;

  private final VersionConvert versionConvert;

  public VersionVO getVersionVO() {
    Version version = dockerClient.versionCmd().exec();
    return versionConvert.convert(version);
  }
}
