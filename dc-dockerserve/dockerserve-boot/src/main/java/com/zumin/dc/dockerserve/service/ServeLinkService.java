package com.zumin.dc.dockerserve.service;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.dockerserve.mapper.ServeLinkMapper;
import com.zumin.dc.dockerserve.mapper.ServeMapper;
import com.zumin.dc.dockerserve.pojo.body.ServeLinkSaveBody;
import com.zumin.dc.dockerserve.pojo.body.ServeSaveBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeLinkEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务链接服务类
 */
@Service
@RequiredArgsConstructor
public class ServeLinkService extends ServiceImpl<ServeLinkMapper, ServeLinkEntity> {

  /**
   * 链接名称的最大长度
   */
  private static final int NAME_MAX_LENGTH = 16;
  private final ServeMapper serveMapper;

  /**
   * 根据服务标识列出其所链接的服务
   *
   * @param serveIndicate 服务标识
   * @return 链接服务列表
   */
  public List<ServeLinkEntity> listByServeIndicate(String serveIndicate) {
    return list(Wrappers.lambdaQuery(ServeLinkEntity.class).eq(ServeLinkEntity::getServeIndicate, serveIndicate));
  }

  /**
   * 根据服务标识批量删除链接
   *
   * @param serveIndicates 服务标识列表
   */
  public void removeByServeIndicates(List<String> serveIndicates) {
    remove(Wrappers.lambdaQuery(ServeLinkEntity.class).in(ServeLinkEntity::getServeIndicate, serveIndicates));
  }

  /**
   * 检查创建服务所使用的名称的合法性
   *
   * @param serveList 服务列表
   * @return 若合法则返回true，否则返回false
   */
  public boolean checkName(List<ServeSaveBody> serveList) {
    List<String> nameList = serveList.stream()
        .map(ServeSaveBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(ServeLinkSaveBody::getName)
        .collect(Collectors.toList());
    return nameList.stream()
        .noneMatch(name -> StrUtil.isBlank(name) || name.length() > NAME_MAX_LENGTH || !StrUtil.isAllCharMatch(name, CharUtil::isLetter));
  }

  /**
   * 保存服务链接信息
   *
   * @param serveList       创建服务信息列表
   * @param serveEntityList 服务列表
   */
  @Transactional
  public void saveServeLink(List<ServeSaveBody> serveList, List<ServeEntity> serveEntityList) {
    IntStream.range(0, serveEntityList.size()).forEach(i -> {
      ServeEntity serveEntity = serveEntityList.get(i);
      ServeSaveBody serveBody = serveList.get(i);
      List<ServeLinkEntity> linkEntityList = ConvertUtils.convert(serveBody.getLinkServeList(), linkBody -> ServeLinkEntity.builder()
          .serveIndicate(serveEntity.getServeIndicate())
          .beServeIndicate(serveMapper.selectById(linkBody.getBeLinkServeId()).getServeIndicate())
          .name(linkBody.getName())
          .build());
      saveBatch(linkEntityList);
    });
  }
}


