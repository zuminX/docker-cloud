package com.zumin.dc.dockerserve.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.file.FileUtils;
import com.zumin.dc.dockerserve.command.ComposeCommand;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ApplicationException;
import com.zumin.dc.dockerserve.mapper.ApplicationMapper;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeBOBuilder;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeServiceBO;
import com.zumin.dc.dockerserve.pojo.bo.ComposeServeInfo;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
   * 获取Compose服务列表
   *
   * @param entity 应用
   * @return 服务列表
   */
  public List<ComposeServeInfo> getComposeServeList(ApplicationEntity entity) {
    updateComposeFile(entity);
    ComposeCommand command = getComposeCommand(entity.getId());
    List<String> containerIdList = command.psContainer();
    List<String> serveIndicateList = command.psServe();
    if (containerIdList.size() != serveIndicateList.size()) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_ERROR);
    }
    return IntStream.range(0, containerIdList.size())
        .mapToObj(i -> new ComposeServeInfo(serveIndicateList.get(i), containerIdList.get(i)))
        .collect(Collectors.toList());
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
   * 根据应用信息构建对应的Compose对象
   *
   * @param application 应用
   * @return DockerCompose对象
   */
  private ComposeBO buildCompose(ApplicationEntity application) {
    List<ServeEntity> serveList = serveService.listByApplicationId(application.getId());
    ComposeBOBuilder builder = ComposeBO.builder();
    serveList.forEach(serve -> {
      List<String> linkList = ConvertUtils.convert(serveLinkService.listByServeIndicate(serve.getServeIndicate()),
          entity -> StrUtil.isBlank(entity.getAlias()) ? entity.getBeServeIndicate() : entity.getBeServeIndicate() + ":" + entity.getAlias());
      ComposeServiceBO composeService = ComposeServiceBO.builder()
          .image(serve.getImageIndicate())
          .container_name(serve.getContainerName())
          .ports(StrUtil.split(serve.getPort(), ';'))
          .environment(StrUtil.split(serve.getEnvironment(), ';'))
          .external_links(linkList)
          .build();
      builder.service(serve.getServeIndicate(), composeService);
    });
    return builder.build();
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

