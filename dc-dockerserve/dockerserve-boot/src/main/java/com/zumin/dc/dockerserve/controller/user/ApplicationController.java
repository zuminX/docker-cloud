package com.zumin.dc.dockerserve.controller.user;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ApplicationConvert;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ApplicationException;
import com.zumin.dc.dockerserve.exception.ImageException;
import com.zumin.dc.dockerserve.exception.ServeException;
import com.zumin.dc.dockerserve.exception.ServeLinkException;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationBody;
import com.zumin.dc.dockerserve.pojo.body.CreateServeBody;
import com.zumin.dc.dockerserve.pojo.body.CreateServeLinkBody;
import com.zumin.dc.dockerserve.pojo.body.ModifyApplicationBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.service.ServeLinkService;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import com.zumin.dc.dockerserve.validator.CheckApplicationAccess;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/application", tags = "应用API接口")
public class ApplicationController extends BaseController {

  private final ApplicationService applicationService;
  private final ImageService imageService;
  private final ServeService serveService;
  private final ServeLinkService serveLinkService;
  private final ApplicationConvert applicationConvert;

  @PostMapping("/create")
  @ApiOperation("创建应用")
  @ApiImplicitParam(name = "body", value = "创建应用的信息", dataTypeClass = CreateApplicationBody.class, required = true)
  public void create(@RequestBody @Valid CreateApplicationBody body) {
    List<CreateServeBody> serveList = body.getServeList();
    assertPort(serveList);
    assertAlias(serveList);
    assertImage(serveList);
    assertLinkServe(serveList);
    assertServeName(serveList);

    saveApplication(body);
  }


  @GetMapping("/start")
  @ApiOperation("启动应用")
  @ApiImplicitParam(name = "application", value = "应用ID", dataTypeClass = Long.class, required = true)
  public void start(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    applicationService.start(application);
  }

  @GetMapping("/stop")
  @ApiOperation("停止应用")
  @ApiImplicitParam(name = "application", value = "应用ID", dataTypeClass = Long.class, required = true)
  public void stop(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    applicationService.stop(application);
  }

  @GetMapping("/list")
  @ApiOperation("根据名称查询当前用户的应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public Page<ApplicationEntity> listByCurrentUser(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByUserIdAndName(SecurityUtils.getUserId(), name));
  }

  @GetMapping("/listShare")
  @ApiOperation("根据名称查询共享的应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public Page<ApplicationEntity> listShare(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByShareAndName(true, name));
  }

  @PostMapping("/modify")
  @ApiOperation("修改应用信息")
  @ApiImplicitParam(name = "body", value = "修改应用的信息", dataTypeClass = ModifyApplicationBody.class, required = true)
  public void modify(@RequestBody ModifyApplicationBody body) {
    ApplicationEntity entity = applicationService.getById(body.getId());
    if (!DockerServeUtils.checkAccess(entity)) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_UNAUTHORIZED_ACCESS);
    }
    applicationService.updateById(PublicUtils.selectiveAssign(body, entity));
  }

  /**
   * 保存应用
   *
   * @param body 创建应用的信息
   */
  @Transactional
  protected void saveApplication(CreateApplicationBody body) {
    ApplicationEntity application = applicationConvert.convert(body, SecurityUtils.getUserId());
    applicationService.save(application);
    List<ServeEntity> serveEntityList = serveService.saveServe(body.getServeList(), application.getId());
    serveLinkService.saveServeLink(body.getServeList(), serveEntityList);
  }

  /**
   * 检查创建服务中的端口信息的合法性
   *
   * @param serveList 创建服务信息列表
   */
  private void assertPort(List<CreateServeBody> serveList) {
    List<List<Integer>> portList = ConvertUtils.convert(serveList, CreateServeBody::getPortList);
    portList.forEach(ports -> {
      Set<Integer> portSet = new HashSet<>();
      ports.forEach(port -> {
        if (!DockerServeUtils.checkPort(port) || portSet.contains(port)) {
          throw new ServeException(DockerServeStatusCode.SERVE_CREATE_PORT_ILLEGAL);
        }
        portSet.add(port);
      });
    });
  }

  /**
   * 检查创建服务所使用的镜像的可访问性
   *
   * @param serveList 服务列表
   */
  private void assertImage(List<CreateServeBody> serveList) {
    List<Integer> imageIdList = ConvertUtils.convert(serveList, CreateServeBody::getImageId);
    imageIdList.stream()
        .filter(imageId -> !DockerServeUtils.checkAccess(imageService.getById(imageId)))
        .forEach(imageId -> {
          throw new ImageException(DockerServeStatusCode.IMAGE_UNAUTHORIZED_ACCESS);
        });
  }

  /**
   * 检查创建服务中链接到的服务的可访问性
   *
   * @param serveList 创建服务信息列表
   */
  private void assertLinkServe(List<CreateServeBody> serveList) {
    serveList.stream()
        .map(CreateServeBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(CreateServeLinkBody::getBeLinkServeId)
        .map(serveService::getById)
        .filter(byId -> !DockerServeUtils.checkAccess(byId))
        .forEach(byId -> {
          throw new ServeException(DockerServeStatusCode.SERVE_UNAUTHORIZED_ACCESS);
        });
  }

  private void assertAlias(List<CreateServeBody> serveList) {
    if (!serveLinkService.checkAlias(serveList)) {
      throw new ServeLinkException(DockerServeStatusCode.SERVE_LINK_ALIAS_ILLEGAL);
    }
  }

  private void assertServeName(List<CreateServeBody> serveList) {
    List<String> serveNameList = ConvertUtils.convert(serveList, CreateServeBody::getName);
    serveNameList.stream()
        .map(serveService::getByName)
        .filter(Objects::nonNull)
        .forEach(entity -> {
          throw new ServeException(DockerServeStatusCode.SERVE_NAME_EXISTS);
        });
  }
}
