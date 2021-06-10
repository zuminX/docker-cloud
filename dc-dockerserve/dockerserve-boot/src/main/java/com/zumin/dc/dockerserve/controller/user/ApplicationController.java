package com.zumin.dc.dockerserve.controller.user;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ApplicationConvert;
import com.zumin.dc.dockerserve.convert.ServeConvert;
import com.zumin.dc.dockerserve.convert.ServeLinkConvert;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ApplicationException;
import com.zumin.dc.dockerserve.exception.ImageException;
import com.zumin.dc.dockerserve.exception.ServeException;
import com.zumin.dc.dockerserve.exception.ServeLinkException;
import com.zumin.dc.dockerserve.pojo.body.ApplicationModifyBody;
import com.zumin.dc.dockerserve.pojo.body.ApplicationSaveBody;
import com.zumin.dc.dockerserve.pojo.body.ServeLinkSaveBody;
import com.zumin.dc.dockerserve.pojo.body.ServeSaveBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeLinkEntity;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeLinkDetailVO;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.service.ServeLinkService;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import com.zumin.dc.dockerserve.validator.CheckApplicationAccess;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
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
  private final ServeConvert serveConvert;
  private final ServeLinkConvert serveLinkConvert;

  @PostMapping("/save")
  @ApiOperation("保存应用")
  @ApiImplicitParam(name = "body", value = "保存应用的信息", dataTypeClass = ApplicationSaveBody.class, required = true)
  public void create(@RequestBody @Valid ApplicationSaveBody body) {
    List<ServeSaveBody> serveList = body.getServeList();
    assertPort(serveList);
    assertLinkName(serveList);
    assertImage(serveList);
    assertLinkServe(serveList);
    assertServeName(serveList);

    Long applicationId = body.getId();
    if (applicationId != null) {
      ApplicationEntity entity = applicationService.getById(body.getId());
      if (!DockerServeUtils.checkAccess(entity)) {
        throw new ApplicationException(DockerServeStatusCode.APPLICATION_UNAUTHORIZED_ACCESS);
      }
      updateApplication(body);
    } else {
      saveApplication(body);
    }
  }

  @GetMapping("/detail")
  @ApiOperation("查看应用详情")
  @ApiImplicitParam(name = "application", value = "应用ID", dataTypeClass = Long.class, required = true)
  public ApplicationDetailVO detail(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    List<ServeEntity> serveEntityList = serveService.listByApplicationId(application.getId());
    List<ServeDetailVO> serveDetailVOList = new ArrayList<>();
    for (ServeEntity serveEntity : serveEntityList) {
      List<ServeLinkEntity> serveLinkEntities = serveLinkService.listByServeIndicate(serveEntity.getServeIndicate());
      List<ServeLinkDetailVO> detailVOList = ConvertUtils.convert(serveLinkEntities,
          serveLink -> serveLinkConvert.convert(serveLink, serveService.getByIndicate(serveLink.getServeIndicate())));
      ImageEntity imageEntity = imageService.getByIndicate(serveEntity.getImageIndicate());
      serveDetailVOList.add(serveConvert.convertToDetailVO(serveEntity, imageEntity.getId(), imageEntity.getName(), detailVOList));
    }
    return applicationConvert.convertToDetail(application, serveDetailVOList);
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
  @ApiImplicitParam(name = "body", value = "修改应用的信息", dataTypeClass = ApplicationModifyBody.class, required = true)
  public void modify(@RequestBody ApplicationModifyBody body) {
    ApplicationEntity entity = applicationService.getById(body.getId());
    if (!DockerServeUtils.checkAccess(entity)) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_UNAUTHORIZED_ACCESS);
    }
    applicationService.updateById(PublicUtils.selectiveAssign(body, entity));
  }

  /**
   * 更新应用
   *
   * @param body 保存应用的信息
   */
  @Transactional
  protected void updateApplication(ApplicationSaveBody body) {
    // TODO 直接删除会影响依赖该服务的应用
    Long applicationId = body.getId();
    List<ServeEntity> serveList = serveService.listByApplicationId(applicationId);

    applicationService.removeById(applicationId);
    serveService.removeByIds(ConvertUtils.convert(serveList, ServeEntity::getId));
    serveLinkService.removeByServeIndicates(ConvertUtils.convert(serveList, ServeEntity::getServeIndicate));

    saveApplication(body);
  }

  /**
   * 保存应用
   *
   * @param body 保存应用的信息
   */
  @Transactional
  protected void saveApplication(ApplicationSaveBody body) {
    ApplicationEntity application = applicationConvert.convertToEntity(body, SecurityUtils.getUserId());
    applicationService.save(application);
    List<ServeEntity> serveEntityList = serveService.saveServe(body.getServeList(), application.getId());
    serveLinkService.saveServeLink(body.getServeList(), serveEntityList);
  }

  /**
   * 检查保存服务中的端口信息的合法性
   *
   * @param serveList 保存服务信息列表
   */
  private void assertPort(List<ServeSaveBody> serveList) {
    List<Set<Integer>> portList = ConvertUtils.convert(serveList, ServeSaveBody::getPortList);
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
   * 检查保存服务所使用的镜像的可访问性
   *
   * @param serveList 服务列表
   */
  private void assertImage(List<ServeSaveBody> serveList) {
    List<Integer> imageIdList = ConvertUtils.convert(serveList, ServeSaveBody::getImageId);
    imageIdList.stream()
        .filter(imageId -> !DockerServeUtils.checkAccess(imageService.getById(imageId)))
        .forEach(imageId -> {
          throw new ImageException(DockerServeStatusCode.IMAGE_UNAUTHORIZED_ACCESS);
        });
  }

  /**
   * 检查保存服务中链接到的服务的可访问性
   *
   * @param serveList 保存服务信息列表
   */
  private void assertLinkServe(List<ServeSaveBody> serveList) {
    serveList.stream()
        .map(ServeSaveBody::getLinkServeList)
        .flatMap(Collection::stream)
        .map(ServeLinkSaveBody::getBeLinkServeId)
        .map(serveService::getById)
        .filter(byId -> !DockerServeUtils.checkAccess(byId))
        .forEach(byId -> {
          throw new ServeException(DockerServeStatusCode.SERVE_UNAUTHORIZED_ACCESS);
        });
  }

  /**
   * 检查保存服务中链接到的链接服务名称的合法性
   *
   * @param serveList 服务列表
   */
  private void assertLinkName(List<ServeSaveBody> serveList) {
    if (!serveLinkService.checkName(serveList)) {
      throw new ServeLinkException(DockerServeStatusCode.SERVE_LINK_NAME_ILLEGAL);
    }
  }

  /**
   * 检查保存服务中服务名称的合法性
   *
   * @param serveList 服务列表
   */
  private void assertServeName(List<ServeSaveBody> serveList) {
    List<String> serveNameList = ConvertUtils.convert(serveList, ServeSaveBody::getName);
    serveNameList.stream()
        .map(serveService::getByName)
        .filter(Objects::nonNull)
        .forEach(entity -> {
          throw new ServeException(DockerServeStatusCode.SERVE_NAME_EXISTS);
        });
  }
}
