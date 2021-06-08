package com.zumin.dc.dockerserve.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.pojo.body.ServeCreateBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import java.util.List;
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
   * 列出指定用户的所有服务
   *
   * @param userId 用户ID
   * @return 用户所有的服务
   */
  public List<ServeEntity> listByUserId(Long userId) {
    return list(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getUserId, userId));
  }

  /**
   * 保存服务
   *
   * @param serveList     创建服务信息列表
   * @param applicationId 应用ID
   * @return 服务列表
   */
  @Transactional
  public List<ServeEntity> saveServe(List<ServeCreateBody> serveList, Long applicationId) {
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

  public ServeEntity getByName(String name) {
    return getOne(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getName, name));
  }

  public ServeEntity getByIndicate(String indicate) {
    return getOne(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getServeIndicate, indicate));
  }
}




