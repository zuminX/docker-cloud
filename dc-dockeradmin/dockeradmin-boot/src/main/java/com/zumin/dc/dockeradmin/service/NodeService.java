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
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeService {

  private final DockerClient dockerClient;

  public List<SwarmNode> listOfAllNodes() {
    return dockerClient.listSwarmNodesCmd().exec();
  }

  public void deleteNode(String nodeId) {
    //TODO 待完成
  }

  public void updateStatusNode(String nodeId, String status) {
    if (isValidStatus(status)) {
      SwarmNode node = dockerClient.listSwarmNodesCmd().withIdFilter(Collections.singletonList(nodeId)).exec().get(0);
      SwarmNodeSpec spec = node.getSpec().withAvailability(SwarmNodeAvailability.valueOf(status));
      dockerClient.updateSwarmNodeCmd().withSwarmNodeSpec(spec).exec();
    }
  }

  public void updateRoleNode(String nodeId, String role) {
    if (isValidRole(role)) {
      SwarmNode node = dockerClient.listSwarmNodesCmd().withIdFilter(Collections.singletonList(nodeId)).exec().get(0);
      SwarmNodeSpec spec = node.getSpec().withRole(SwarmNodeRole.valueOf(role));
      dockerClient.updateSwarmNodeCmd().withSwarmNodeSpec(spec).exec();
    }
  }

  private boolean isValidStatus(String status) {
    return Stream.of(NodeAvailabilityStatus.values()).anyMatch(nodeStatus -> nodeStatus.getValue().equalsIgnoreCase(status));
  }

  private boolean isValidRole(String role) {
    return Stream.of(NodeRole.values()).anyMatch(nodeRole -> nodeRole.getValue().equalsIgnoreCase(role));
  }
}