package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
import com.zumin.dc.dockeradmin.enums.NetworkType;
import com.zumin.dc.dockeradmin.pojo.vo.NetworkStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker网络的服务层类
 */
@Service
@RequiredArgsConstructor
public class NetworkService {

  private final DockerClient dockerClient;

  /**
   * 列出所有网络
   *
   * @return 网络列表
   */
  public List<Network> list() {
    return dockerClient.listNetworksCmd().exec();
  }

  /**
   * 删除指定的网络
   *
   * @param id 网络ID
   */
  public void remove(String id) {
    dockerClient.removeNetworkCmd(id).exec();
  }

  /**
   * 获取网络的统计信息
   *
   * @return 网络的统计信息
   */
  public NetworkStatsVO getStatistics() {
    List<Network> networks = list();
    List<Network> buildInNetworks = dockerClient.listNetworksCmd().withFilter("type", NetworkType.BUILTIN.getNameAsArray()).exec();
    List<Network> customNetworks = dockerClient.listNetworksCmd().withFilter("type", NetworkType.CUSTOM.getNameAsArray()).exec();
    return NetworkStatsVO.builder().total(networks.size()).buildInTotal(buildInNetworks.size()).customTotal(customNetworks.size()).build();
  }
}
