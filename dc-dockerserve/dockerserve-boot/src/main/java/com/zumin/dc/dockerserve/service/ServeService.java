package com.zumin.dc.dockerserve.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationServeInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServeService extends ServiceImpl<ServeMapper, ServeEntity> {

  private final ImageMapper imageMapper;

  public List<ServeEntity> listByApplicationId(Long applicationId) {
    return list(Wrappers.lambdaQuery(ServeEntity.class).eq(ServeEntity::getApplicationId, applicationId));
  }

  @Transactional
  public List<ServeEntity> saveList(List<CreateApplicationServeBody> serveList, Long applicationId) {
    List<ServeEntity> serveEntityList = ConvertUtils.convert(serveList, serveBody -> ServeEntity.builder()
        .name(serveBody.getName())
        .serveIndicate(PublicUtils.getRandomIdentity() + "-" + SecurityUtils.getUserId())
        .imageIndicate(imageMapper.selectById(serveBody.getImageId()).getIndicate())
        .containerName(serveBody.getName())
        .port(StrUtil.join(";", serveBody.getPortList()))
        .applicationId(applicationId)
        .build());
    saveBatch(serveEntityList);
    return serveEntityList;
  }

  public List<ApplicationServeInfo> getInfo(Long applicationId) {
    List<ServeEntity> serveEntityList = listByApplicationId(applicationId);

    return null;
  }

  public List<ServeEntity> listShareOrUserIdByName(Long userId, String name) {
    return baseMapper.selectByUserIdOrShareAndNameLike(userId, name);
  }
}




