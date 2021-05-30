package com.zumin.dc.dockerserve.controller.user;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ImageConvert;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileJarProcessor;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ImageException;
import com.zumin.dc.dockerserve.pojo.body.BuildImageBody;
import com.zumin.dc.dockerserve.pojo.body.ModifyImageBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.vo.ImageNameVO;
import com.zumin.dc.dockerserve.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/image", tags = "Docker镜像API接口")
public class ImageController extends BaseController {

  private final ImageService imageService;
  private final ImageConvert imageConvert;

  @PostMapping("/build")
  public void buildImage(BuildImageBody body) {
    imageService.build(body, DockerFileProcessor.DEFAULT);
  }

  @PostMapping("/buildJar")
  public void buildJarImage(BuildImageBody body, @RequestParam(name = "javaVersion", required = false) String javaVersion) {
    imageService.build(body, new DockerFileJarProcessor(javaVersion));
  }

  @GetMapping("/search")
  public List<ImageNameVO> searchByName(@RequestParam(value = "name") String name) {
    return ConvertUtils.convert(imageService.listShareOrUserIdByName(SecurityUtils.getUserId(), name), imageConvert::convert);
  }

  @GetMapping("/list")
  public Page<ImageEntity> listByCurrentUser(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> imageService.listByUserIdAndName(SecurityUtils.getUserId(), name));
  }

  @GetMapping("/listShare")
  public Page<ImageEntity> listShare(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> imageService.listByShareAndName(true, name));
  }

  @PostMapping("/modify")
  public void modify(@RequestBody ModifyImageBody body) {
    ImageEntity entity = imageService.getById(body.getId());
    if (entity == null || !imageService.checkImageAccess(entity)) {
      throw new ImageException(DockerServeStatusCode.IMAGE_UNAUTHORIZED_ACCESS);
    }
    imageService.updateById(PublicUtils.selectiveAssign(body, entity));
  }
}
