package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.SwarmNode;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.NodeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/node", tags = "Docker节点API接口")
public class NodeController {

  private final NodeService nodeService;

  @GetMapping("/list")
  public List<SwarmNode> listOfAllNodes() {
    return nodeService.listOfAllNodes();
  }

  @DeleteMapping("/{nodeId}")
  public void deleteNode(@PathVariable String nodeId) {
    nodeService.deleteNode(nodeId);
  }

  @PutMapping("/{nodeId}/status")
  public void updateStatusNode(@PathVariable String nodeId, @RequestParam("q") String status) {
    nodeService.updateStatusNode(nodeId, status);
  }

  @PutMapping("/{nodeId}/role")
  public void updateRoleNode(@PathVariable String nodeId, @RequestParam("q") String role) {
    nodeService.updateRoleNode(nodeId, role);
  }
}