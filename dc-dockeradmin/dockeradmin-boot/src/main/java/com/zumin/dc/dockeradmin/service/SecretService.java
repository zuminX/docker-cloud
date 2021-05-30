package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Secret;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecretService {

  private final DockerClient dockerClient;

  public List<Secret> listOfAllSecret() {
    return dockerClient.listSecretsCmd().exec();
  }

  public void deleteSecret(String secretId) {
    dockerClient.removeSecretCmd(secretId).exec();
  }
}