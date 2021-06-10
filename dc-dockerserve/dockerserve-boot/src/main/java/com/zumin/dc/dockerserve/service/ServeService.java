package com.zumin.dc.dockerserve.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.convert.ServeConvert;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.pojo.body.ServeSaveBody;
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
  private final ServeConvert serveConvert;

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
  public List<ServeEntity> saveServe(List<ServeSaveBody> serveList, Long applicationId) {
    Long userId = SecurityUtils.getUserId();
    List<ServeEntity> serveEntityList = ConvertUtils.convert(serveList,
        serveBody -> serveConvert.convertToEntity(serveBody, applicationId).toBuilder()
            .userId(userId)
            .serveIndicate(generateServeIndicate(userId))
            .imageIndicate(imageMapper.selectById(serveBody.getImageId()).getIndicate())
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
   * 获取指定名称的服务
   *
   * @param name 服务名称
   * @return 服务
   */
  public ServeEntity getByName(String name) {
    return getOne(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getName, name));
  }

  /**
   * 获取指定标识的服务
   *
   * @param indicate 服务标识
   * @return 服务
   */
  public ServeEntity getByIndicate(String indicate) {
    return getOne(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getServeIndicate, indicate));
  }

  private String generateServeIndicate(Long userId) {
    return PublicUtils.getRandomIdentity() + "-" + userId;
  }

}




