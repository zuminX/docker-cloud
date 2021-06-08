package com.zumin.dc.dockeradmin.controller;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ImageConvert;
import com.zumin.dc.dockeradmin.pojo.vo.image.ImageVO;
import com.zumin.dc.dockeradmin.pojo.vo.image.InspectImageResponseVO;
import com.zumin.dc.dockeradmin.service.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/image", tags = "Docker镜像API接口")
public class ImageController {

  private final ImageService imageService;
  private final ImageConvert imageConvert;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的镜像信息")
  public List<ImageVO> listImage() {
    return ConvertUtils.convert(imageService.list(), imageConvert::convert);
  }

  @GetMapping("/info")
  @ApiOperation("获取指定的镜像的详情")
  @ApiImplicitParam(name = "imageId", value = "镜像ID", dataTypeClass = String.class, required = true)
  public InspectImageResponseVO getImageInfo(@RequestParam("imageId") String imageId) {
    return imageConvert.convert(imageService.getById(imageId));
  }

  @GetMapping("/delete")
  @ApiOperation("删除指定的镜像")
  @ApiImplicitParam(name = "imageId", value = "镜像ID", dataTypeClass = String.class, required = true)
  public void deleteImage(@RequestParam("imageId") String imageId) {
    imageService.remove(imageId);
  }
}
