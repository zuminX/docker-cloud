package com.zumin.dc.dockeradmin.controller;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ImageConvert;
import com.zumin.dc.dockeradmin.pojo.vo.image.InspectImageResponseVO;
import com.zumin.dc.dockeradmin.service.ImageService;
import java.util.List;
import java.util.stream.Collectors;
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
  public List<InspectImageResponseVO> listAll() {
    return ConvertUtils.convert(imageService.listOfAllImages(), imageConvert::convert);
  }

  @GetMapping("/query")
  public InspectImageResponseVO getById(@RequestParam("imageId") String imageId) {
    return imageConvert.convert(imageService.listImage(imageId));
  }

  @DeleteMapping("/delete")
  public void delete(@RequestParam("imageId") String imageId) {
    imageService.removeImage(imageId);
  }
}
