package com.zumin.dc.dockerserve.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.file.FileUtils;
import com.zumin.dc.dockerserve.command.DockerComposeCommand;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ApplicationException;
import com.zumin.dc.dockerserve.mapper.ApplicationMapper;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeBOBuilder;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO.ComposeServiceBO;
import com.zumin.dc.dockerserve.pojo.bo.ComposeServeInfo;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeBody;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeLinkBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

@Service
@RequiredArgsConstructor
public class ApplicationService extends ServiceImpl<ApplicationMapper, ApplicationEntity> {

  private final static String COMPOSE_DIRECTORY = "/docker-cloud/compose";

  private final ServeService serveService;
  private final ServeLinkService serveLinkService;

  public ComposeBO buildCompose(ApplicationEntity application) {
    List<ServeEntity> serveList = serveService.listByApplicationId(application.getId());
    ComposeBOBuilder builder = ComposeBO.builder();
    for (ServeEntity serve : serveList) {
      List<String> linkList = serveLinkService.listByServeIndicate(serve.getServeIndicate())
          .stream()
          .map(entity -> StrUtil.isBlank(entity.getAlias()) ? entity.getBeServeIndicate() : entity.getBeServeIndicate() + ":" + entity.getAlias())
          .collect(Collectors.toList());
      ComposeServiceBO composeService = ComposeServiceBO.builder()
          .image(serve.getImageIndicate())
          .container_name(serve.getContainerName())
          .ports(StrUtil.split(serve.getPort(), ';'))
          .environment(StrUtil.split(serve.getEnvironment(), ';'))
          .external_links(linkList)
          .build();
      builder.service(serve.getServeIndicate(), composeService);
    }
    return builder.build();
  }

  public boolean checkPort(List<CreateApplicationServeBody> serveList) {
    List<List<Integer>> portList = ConvertUtils.convert(serveList, CreateApplicationServeBody::getPortList);
    for (List<Integer> ports : portList) {
      Set<Integer> portSet = new HashSet<>();
      for (Integer port : ports) {
        if (port == null || port <= 0 || port > 60999) {
          return false;
        }
        if (portSet.contains(port)) {
          return false;
        }
        portSet.add(port);
      }
    }
    return true;
  }

  public boolean checkLinkServeAccess(List<CreateApplicationServeBody> serveList) {
    List<ApplicationEntity> applicationList = serveList.stream()
        .map(CreateApplicationServeBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(CreateApplicationServeLinkBody::getBeLinkServeId)
        .map(serveService::getById)
        .map(ServeEntity::getApplicationId)
        .map(this::getById)
        .collect(Collectors.toList());
    return applicationList.stream().noneMatch(application -> application == null || !DockerServeUtils.checkAccess(application));
  }

  /**
   * 启动应用
   *
   * @param compose       应用组成
   * @param applicationId 应用ID
   */
  public void start(ComposeBO compose, Long applicationId) {
    DockerComposeCommand command = getDockerComposeCommand(applicationId);
    File file = new File(command.getPath());
    FileUtil.touch(file);
    FileUtil.writeUtf8String(new Yaml().dumpAs(compose, Tag.MAP, null), file);
    command.up();
  }

  /**
   * 停止应用
   *
   * @param applicationId 应用ID
   */
  public void stop(Long applicationId) {
    DockerComposeCommand command = getDockerComposeCommand(applicationId);
    File file = new File(command.getPath());
    if (!file.exists() || FileUtil.isEmpty(file)) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_NOT_START);
    }
    command.stop();
  }

  /**
   * 获取Compose服务列表
   *
   * @param applicationId 应用ID
   * @return 服务列表
   */
  public List<ComposeServeInfo> getComposeServeList(Long applicationId) {
    DockerComposeCommand command = getDockerComposeCommand(applicationId);
    List<String> containerIdList = command.psContainer();
    List<String> serveIndicateList = command.psServe();
    if (containerIdList.size() != serveIndicateList.size()) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_ERROR);
    }
    return IntStream.range(0, containerIdList.size())
        .mapToObj(i -> new ComposeServeInfo(serveIndicateList.get(i), containerIdList.get(i)))
        .collect(Collectors.toList());
  }

  public List<ApplicationEntity> listByUserIdAndName(Long userId, String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).eq(ApplicationEntity::getUserId, userId).like(StrUtil.isNotBlank(name),
        ApplicationEntity::getName, name));
  }

  public List<ApplicationEntity> listByShareAndName(boolean share, String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).eq(ApplicationEntity::getShare, share).like(StrUtil.isNotBlank(name),
        ApplicationEntity::getName, name));
  }

  public List<ApplicationEntity> listByName(String name) {
    return list(Wrappers.lambdaQuery(ApplicationEntity.class).like(StrUtil.isNotBlank(name), ApplicationEntity::getName, name));
  }

  private DockerComposeCommand getDockerComposeCommand(Long applicationId) {
    String path = FileUtils.connect(COMPOSE_DIRECTORY, applicationId + ".yaml");
    return new DockerComposeCommand(path);
  }

}

