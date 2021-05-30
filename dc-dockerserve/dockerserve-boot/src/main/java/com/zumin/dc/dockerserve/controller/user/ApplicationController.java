package com.zumin.dc.dockerserve.controller.user;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ApplicationConvert;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ApplicationException;
import com.zumin.dc.dockerserve.exception.ApplicationServeException;
import com.zumin.dc.dockerserve.exception.ApplicationServeLinkException;
import com.zumin.dc.dockerserve.exception.ImageException;
import com.zumin.dc.dockerserve.pojo.bo.ComposeBO;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationBody;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeBody;
import com.zumin.dc.dockerserve.pojo.body.ModifyApplicationBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.service.ServeLinkService;
import com.zumin.dc.dockerserve.service.ServeService;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import com.zumin.dc.dockerserve.validator.CheckApplicationAccess;
import java.util.List;
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
  public void create(@RequestBody CreateApplicationBody body) {
    List<CreateApplicationServeBody> serveList = body.getServeList();
    if (!applicationService.checkPort(serveList)) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_PORT_ILLEGAL);
    }
    if (!imageService.checkImageAccess(serveList)) {
      throw new ImageException(DockerServeStatusCode.IMAGE_UNAUTHORIZED_ACCESS);
    }
    if (!applicationService.checkLinkServeAccess(serveList)) {
      throw new ApplicationServeException(DockerServeStatusCode.SERVE_UNAUTHORIZED_ACCESS);
    }
    if (!serveLinkService.checkAlias(serveList)) {
      throw new ApplicationServeLinkException(DockerServeStatusCode.SERVE_LINK_ALIAS_ILLEGAL);
    }
    saveApplication(body);
  }

  @GetMapping("/start")
  public void start(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    ComposeBO compose = applicationService.buildCompose(application);
    applicationService.start(compose, application.getId());
  }

  @GetMapping("/stop")
  public void stop(@RequestParam("application") @CheckApplicationAccess ApplicationEntity application) {
    applicationService.stop(application.getId());
  }

  @GetMapping("/listByName")
  public Page<ApplicationEntity> listByName(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByName(name));
  }

  @GetMapping("/list")
  public Page<ApplicationEntity> listByCurrentUser(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByUserIdAndName(SecurityUtils.getUserId(), name));
  }

  @GetMapping("/listShare")
  public Page<ApplicationEntity> listShare(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByShareAndName(true, name));
  }

  @PostMapping("/modify")
  public void modify(@RequestBody ModifyApplicationBody body) {
    ApplicationEntity entity = applicationService.getById(body.getId());
    if (!DockerServeUtils.checkAccess(entity)) {
      throw new ApplicationException(DockerServeStatusCode.APPLICATION_UNAUTHORIZED_ACCESS);
    }
    applicationService.updateById(PublicUtils.selectiveAssign(body, entity));
  }

  @Transactional
  protected void saveApplication(CreateApplicationBody body) {
    ApplicationEntity application = applicationConvert.convert(body, SecurityUtils.getUserId());
    applicationService.save(application);
    List<ServeEntity> serveEntityList = serveService.saveList(body.getServeList(), application.getId());
    serveLinkService.saveServeLink(body.getServeList(), serveEntityList);
  }
}
