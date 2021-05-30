package com.zumin.dc.dockeradmin.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
import com.zumin.dc.dockeradmin.enums.NetworkType;
import com.zumin.dc.dockeradmin.pojo.vo.NetworkStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkService {

  private final DockerClient dockerClient;

  public List<Network> listOfAllNetworks() {
    return dockerClient.listNetworksCmd().exec();
  }

  public void removeNetwork(String networkId) {
    dockerClient.removeNetworkCmd(networkId).exec();
  }

  public NetworkStatsVO getStatistics() {
    List<Network> networks = listOfAllNetworks();
    List<Network> buildInNetworks = dockerClient.listNetworksCmd().withFilter("type", NetworkType.BUILTIN.getNameAsArray()).exec();
    List<Network> customNetworks = dockerClient.listNetworksCmd().withFilter("type", NetworkType.CUSTOM.getNameAsArray()).exec();
    return new NetworkStatsVO(networks.size(), buildInNetworks.size(), customNetworks.size());
  }
}
