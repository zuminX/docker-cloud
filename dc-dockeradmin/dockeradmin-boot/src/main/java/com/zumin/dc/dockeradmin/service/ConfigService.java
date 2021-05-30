package com.zumin.dc.dockeradmin.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Config;
import com.zumin.dc.dockeradmin.convert.ConfigConvert;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

  private final DockerClient dockerClient;

  public List<Config> listOfAllConfig() {
    return dockerClient.listConfigsCmd().exec();
  }

  public void deleteConfig(String configId) {
    dockerClient.removeConfigCmd(configId).exec();
  }
}