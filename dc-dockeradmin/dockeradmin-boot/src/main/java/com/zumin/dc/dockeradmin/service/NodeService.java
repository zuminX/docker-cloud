package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.SwarmNode;
import com.github.dockerjava.api.model.SwarmNodeAvailability;
import com.github.dockerjava.api.model.SwarmNodeRole;
import com.github.dockerjava.api.model.SwarmNodeSpec;
import com.zumin.dc.dockeradmin.enums.NodeAvailabilityStatus;
import com.zumin.dc.dockeradmin.enums.NodeRole;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Docker节点的服务层类
 */
@Service
@RequiredArgsConstructor
public class NodeService {

  private final DockerClient dockerClient;

  /**
   * 列出所有的节点
   *
   * @return 节点列表
   */
  public List<SwarmNode> list() {
    return dockerClient.listSwarmNodesCmd().exec();
  }

  /**
   * 删除指定的节点
   *
   * @param id 节点ID
   */
  public void remove(String id) {
    //TODO 待完成
  }

  /**
   * 更新指定节点的状态
   *
   * @param id     节点ID
   * @param status 状态
   */
  public void updateStatus(String id, NodeAvailabilityStatus status) {
    SwarmNode node = dockerClient.listSwarmNodesCmd().withIdFilter(Collections.singletonList(id)).exec().get(0);
    SwarmNodeSpec spec = node.getSpec().withAvailability(SwarmNodeAvailability.valueOf(status.getName()));
    dockerClient.updateSwarmNodeCmd().withSwarmNodeSpec(spec).exec();
  }

  /**
   * 更新指定节点的角色
   *
   * @param id 节点ID
   * @param role 角色
   */
  public void updateRole(String id, NodeRole role) {
    SwarmNode node = dockerClient.listSwarmNodesCmd().withIdFilter(Collections.singletonList(id)).exec().get(0);
    SwarmNodeSpec spec = node.getSpec().withRole(SwarmNodeRole.valueOf(role.getName()));
    dockerClient.updateSwarmNodeCmd().withSwarmNodeSpec(spec).exec();
  }
}