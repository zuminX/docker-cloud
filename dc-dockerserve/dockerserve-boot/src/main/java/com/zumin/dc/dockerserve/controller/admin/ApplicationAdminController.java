package com.zumin.dc.dockerserve.controller.admin;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.service.ApplicationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/admin/application", tags = "管理应用API接口")
public class ApplicationAdminController extends BaseController {

  private final ApplicationService applicationService;

  @PostMapping("/save")
  @ApiOperation("保存应用")
  @ApiImplicitParam(name = "entity", value = "修改后的应用对象", dataTypeClass = ApplicationEntity.class, required = true)
  public void save(@RequestBody ApplicationEntity entity) {
    entity.setUpdateTime(LocalDateTime.now());
    applicationService.save(entity);
  }

  @GetMapping("/listByName")
  @ApiOperation("根据名称查询应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public Page<ApplicationEntity> listByName(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> applicationService.listByName(name));
  }
}
