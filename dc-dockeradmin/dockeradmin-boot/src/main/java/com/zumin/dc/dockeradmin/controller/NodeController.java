package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.SwarmNode;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.enums.NodeAvailabilityStatus;
import com.zumin.dc.dockeradmin.enums.NodeRole;
import com.zumin.dc.dockeradmin.service.NodeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/node", tags = "Docker节点API接口")
public class NodeController {

  private final NodeService nodeService;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的节点信息")
  public List<SwarmNode> listNode() {
    return nodeService.list();
  }

  @DeleteMapping("/remove")
  @ApiOperation("删除指定的节点")
  @ApiImplicitParam(name = "nodeId", value = "节点ID", required = true, dataTypeClass = String.class)
  public void removeNode(@RequestParam("nodeId") String nodeId) {
    nodeService.remove(nodeId);
  }

  @GetMapping("/update/status")
  @ApiOperation("更新指定节点的状态")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeId", value = "节点ID", required = true, dataTypeClass = String.class),
      @ApiImplicitParam(name = "status", value = "节点状态", required = true, dataTypeClass = String.class)
  })
  public void updateNodeStatus(@RequestParam("nodeId") String nodeId, @RequestParam("q") NodeAvailabilityStatus status) {
    nodeService.updateStatus(nodeId, status);
  }

  @PutMapping("/update/role")
  @ApiOperation("更新指定节点的角色")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "nodeId", value = "节点ID", required = true, dataTypeClass = String.class),
      @ApiImplicitParam(name = "role", value = "节点角色", required = true, dataTypeClass = String.class)
  })
  public void updateNodeRole(@RequestParam("nodeId") String nodeId, @RequestParam("q") NodeRole role) {
    nodeService.updateRole(nodeId, role);
  }
}