package com.zumin.dc.dockerserve.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.file.FileUtils;
import com.zumin.dc.dockerserve.command.ComposeCommand;
import com.zumin.dc.dockerserve.mapper.ApplicationMapper;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeBOBuilder;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeServiceBO;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

/**
 * 应用服务类
 */
@Service
@RequiredArgsConstructor
public class ApplicationService extends ServiceImpl<ApplicationMapper, ApplicationEntity> {

  /**
   * 存放Compose文件的目录
   */
  private final static String COMPOSE_DIRECTORY = "/docker-cloud/compose";

  private final ServeService serveService;
  private final ServeLinkService serveLinkService;

  /**
   * 启动应用
   *
   * @param entity 应用
   */
  public void start(ApplicationEntity entity) {
    updateComposeFile(entity);
    getComposeCommand(entity.getId()).up();
  }

  /**
   * 停止应用
   *
   * @param entity 应用
   */
  public void stop(ApplicationEntity entity) {
    updateComposeFile(entity);
    getComposeCommand(entity.getId()).stop();
  }

  /**
   * 根据名称列出当前用户的应用
   *
   * @param userId 用户ID
   * @param name   应用名称
   * @return 应用列表
   */
  public List<ApplicationEntity> listByUserIdAndName(Long userId, String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).eq(ApplicationEntity::getUserId, userId).like(StrUtil.isNotBlank(name),
        ApplicationEntity::getName, name));
  }

  /**
   * 根据名称和是否共享列出应用
   *
   * @param share 是否共享
   * @param name  应用名称
   * @return 应用列表
   */
  public List<ApplicationEntity> listByShareAndName(boolean share, String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).eq(ApplicationEntity::getShare, share).like(StrUtil.isNotBlank(name),
        ApplicationEntity::getName, name));
  }

  /**
   * 根据名称列出应用
   *
   * @param name 应用名称
   * @return 应用列表
   */
  public List<ApplicationEntity> listByName(String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).like(StrUtil.isNotBlank(name), ApplicationEntity::getName, name));
  }

  /**
   * 获取用户应用总数
   *
   * @param userId 用户ID
   * @return 应用总数
   */
  public int countByUserId(Long userId) {
    return count(Wrappers.lambdaQuery(ApplicationEntity.class).eq(ApplicationEntity::getUserId, userId));
  }

  /**
   * 根据应用信息构建对应的Compose对象
   *
   * @param application 应用
   * @return DockerCompose对象
   */
  private ComposeBO buildCompose(ApplicationEntity application) {
    List<ServeEntity> serveList = serveService.listByApplicationId(application.getId());
    ComposeBOBuilder builder = ComposeBO.builder();
    serveList.forEach(serve -> {
      ComposeServiceBO composeService = ComposeServiceBO.builder()
          .image(serve.getImageIndicate())
          .container_name(serve.getServeIndicate())
          .ports(StrUtil.split(serve.getPort(), ';'))
          .environment(StrUtil.split(serve.getEnvironment(), ';'))
          .external_links(CollUtil.union(getApplicationLinks(serveList, serve), getOuterLinks(serve)))
          .build();
      builder.service(serve.getServeIndicate(), composeService);
    });
    return builder.build();
  }

  /**
   * 获取当前服务到该服务所在应用的其他服务的链接
   *
   * @param serveList 服务列表
   * @param nowServe  当前服务
   * @return 链接列表
   */
  private List<String> getApplicationLinks(List<ServeEntity> serveList, ServeEntity nowServe) {
    return serveList.stream()
        .filter(serve -> !serve.getId().equals(nowServe.getId()))
        .filter(serve -> !StrUtil.isBlank(serve.getLinkName()))
        .map(serve -> getExternalLink(serve.getServeIndicate(), serve.getLinkName()))
        .collect(Collectors.toList());
  }

  /**
   * 获得应用的其他外部链接
   *
   * @param serve 服务
   * @return 链接列表
   */
  private List<String> getOuterLinks(ServeEntity serve) {
    return ConvertUtils.convert(serveLinkService.listByServeIndicate(serve.getServeIndicate()),
        entity -> getExternalLink(entity.getBeServeIndicate(), entity.getName()));
  }

  /**
   * 获得外部链接
   *
   * @param serveIndicate 服务标识
   * @param alias         别名
   * @return 链接
   */
  private String getExternalLink(String serveIndicate, String alias) {
    return StrUtil.isBlank(alias) ? serveIndicate : serveIndicate + ":" + alias;
  }

  /**
   * 更新Compose文件
   *
   * @param entity 应用
   */
  private void updateComposeFile(ApplicationEntity entity) {
    ComposeBO compose = buildCompose(entity);
    File file = new File(getComposePath(entity.getId()));
    FileUtil.touch(file);
    FileUtil.writeUtf8String(new Yaml().dumpAs(compose, Tag.MAP, null), file);
  }

  /**
   * 获取指定应用的Compose命令实例
   *
   * @param applicationId 应用ID
   * @return 命令实例
   */
  private ComposeCommand getComposeCommand(Long applicationId) {
    String path = getComposePath(applicationId);
    return new ComposeCommand(path);
  }

  /**
   * 获取指定应用的Compose文件路径
   *
   * @param applicationId 应用ID
   * @return Compose文件路径
   */
  private String getComposePath(Long applicationId) {
    return FileUtils.connect(COMPOSE_DIRECTORY, applicationId + ".yaml");
  }

}

