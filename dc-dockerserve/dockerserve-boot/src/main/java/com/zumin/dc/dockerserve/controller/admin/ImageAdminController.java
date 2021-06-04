package com.zumin.dc.dockerserve.controller.admin;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.service.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/admin/image", tags = "管理镜像API接口")
public class ImageAdminController extends BaseController {

  private final ImageService imageService;

  @PostMapping("/save")
  @ApiOperation("保存镜像")
  @ApiImplicitParam(name = "entity", value = "修改后的镜像对象", dataTypeClass = ImageEntity.class, required = true)
  public void save(@RequestBody ImageEntity entity) {
    imageService.updateById(entity);
  }

  @GetMapping("/listByName")
  @ApiOperation("根据名称查询镜像")
  @ApiImplicitParam(name = "name", value = "镜像名称", dataTypeClass = String.class, required = true)
  public Page<ImageEntity> listByName(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> imageService.listByName(name));
  }
}
