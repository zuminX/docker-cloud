package com.zumin.dc.dockerserve.controller.admin;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.service.ImageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/admin/image", tags = "管理镜像API接口")
public class ImageAdminController extends BaseController {

  private final ImageService imageService;

  @GetMapping("/listAll")
  @ApiOperation("列出所有镜像")
  public Page<ImageEntity> listAll() {
    return getPage(imageService::list);
  }
}
