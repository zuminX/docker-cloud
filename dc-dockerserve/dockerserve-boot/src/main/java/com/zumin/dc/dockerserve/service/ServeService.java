package com.zumin.dc.dockerserve.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.pojo.body.CreateServeBody;
import com.zumin.dc.dockerserve.pojo.body.CreateServeLinkBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务的服务类
 */
@Service
@RequiredArgsConstructor
public class ServeService extends ServiceImpl<ServeMapper, ServeEntity> {

  private final ImageMapper imageMapper;

  /**
   * 列出应用的所有服务
   *
   * @param applicationId 应用ID
   * @return 应用所拥有的服务
   */
  public List<ServeEntity> listByApplicationId(Long applicationId) {
    return list(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getApplicationId, applicationId));
  }

  /**
   * 保存服务
   *
   * @param serveList     创建服务信息列表
   * @param applicationId 应用ID
   * @return 服务列表
   */
  @Transactional
  public List<ServeEntity> saveServe(List<CreateServeBody> serveList, Long applicationId) {
    List<ServeEntity> serveEntityList = ConvertUtils.convert(serveList, serveBody -> ServeEntity.builder()
        .name(serveBody.getName())
        .description(serveBody.getDescription())
        .share(serveBody.getShare())
        .userId(SecurityUtils.getUserId())
        .serveIndicate(PublicUtils.getRandomIdentity() + "-" + SecurityUtils.getUserId())
        .imageIndicate(imageMapper.selectById(serveBody.getImageId()).getIndicate())
        .containerName(serveBody.getName())
        .port(StrUtil.join(";", serveBody.getPortList()))
        .applicationId(applicationId)
        .build());
    saveBatch(serveEntityList);
    return serveEntityList;
  }

  /**
   * 根据名称列出当前用户或共享的服务
   *
   * @param userId 用户ID
   * @param name   服务名称
   * @return 服务列表
   */
  public List<ServeEntity> listShareOrUserIdByName(Long userId, String name) {
    return baseMapper.selectByUserIdOrShareAndNameLike(userId, name);
  }

  /**
   * 获取用户服务总数
   *
   * @param userId 用户ID
   * @return 服务总数
   */
  public int countByUserId(Long userId) {
    return count(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getUserId, userId));
  }

  /**
   * 检查创建服务中链接到的服务的可访问性
   *
   * @param serveList 创建服务信息列表
   * @return 若可访问则返回true，否则返回false
   */
  public boolean checkLinkServeAccess(List<CreateServeBody> serveList) {
    return serveList.stream()
        .map(CreateServeBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(CreateServeLinkBody::getBeLinkServeId)
        .map(this::getById)
        .allMatch(DockerServeUtils::checkAccess);
  }

  /**
   * 检查创建服务中的端口信息的合法性
   *
   * @param serveList 创建服务信息列表
   * @return 若合法则返回true，否则返回false
   */
  public boolean checkPort(List<CreateServeBody> serveList) {
    List<List<Integer>> portList = ConvertUtils.convert(serveList, CreateServeBody::getPortList);
    for (List<Integer> ports : portList) {
      Set<Integer> portSet = new HashSet<>();
      for (Integer port : ports) {
        if (!DockerServeUtils.checkPort(port) || portSet.contains(port)) {
          return false;
        }
        portSet.add(port);
      }
    }
    return true;
  }
}




