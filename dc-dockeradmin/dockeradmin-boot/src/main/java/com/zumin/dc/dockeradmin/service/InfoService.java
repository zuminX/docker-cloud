package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.zumin.dc.dockeradmin.convert.InfoConvert;
import com.zumin.dc.dockeradmin.pojo.vo.InfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {

  private final DockerClient dockerClient;

  private final InfoConvert infoConvert;

  public InfoVO getInfoVO() {
    Info info = dockerClient.infoCmd().exec();
    return infoConvert.convert(info);
  }
}
