package com.zumin.dc.dockerserve.service;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.dockerserve.mapper.ServeLinkMapper;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeBody;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeLinkBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeLinkEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServeLinkService extends ServiceImpl<ServeLinkMapper, ServeLinkEntity> {

  private static final int ALIAS_MAX_LENGTH = 16;
  private final ServeMapper serveMapper;

  public List<ServeLinkEntity> listByServeIndicate(String serveIndicate) {
    return list(Wrappers.lambdaQuery(ServeLinkEntity.class).eq(ServeLinkEntity::getServeIndicate, serveIndicate));
  }

  public boolean checkAlias(List<CreateApplicationServeBody> serveList) {
    List<String> aliasList = serveList.stream()
        .map(CreateApplicationServeBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(CreateApplicationServeLinkBody::getAlias)
        .collect(Collectors.toList());
    return aliasList.stream()
        .noneMatch(alias -> StrUtil.isBlank(alias) || alias.length() > ALIAS_MAX_LENGTH || !StrUtil.isAllCharMatch(alias, CharUtil::isLetter));
  }

  @Transactional
  public void saveServeLink(List<CreateApplicationServeBody> serveList, List<ServeEntity> serveEntityList) {
    IntStream.range(0, serveEntityList.size()).forEach(i -> {
      ServeEntity serveEntity = serveEntityList.get(i);
      CreateApplicationServeBody serveBody = serveList.get(i);
      List<ServeLinkEntity> linkEntityList = serveBody.getLinkServeList().stream()
          .map(linkBody -> ServeLinkEntity.builder()
              .serveIndicate(serveEntity.getServeIndicate())
              .beServeIndicate(serveMapper.selectById(linkBody.getBeLinkServeId()).getServeIndicate())
              .alias(linkBody.getAlias())
              .build())
          .collect(Collectors.toList());
      saveBatch(linkEntityList);
    });
  }
}


