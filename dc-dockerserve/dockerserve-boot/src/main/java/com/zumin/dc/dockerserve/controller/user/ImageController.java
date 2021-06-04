package com.zumin.dc.dockerserve.controller.user;

import static com.zumin.dc.common.mybatis.utils.PageUtils.getPage;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.mybatis.page.Page;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.controller.BaseController;
import com.zumin.dc.dockerserve.convert.ImageConvert;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ImageException;
import com.zumin.dc.dockerserve.pojo.body.BuildImageBody;
import com.zumin.dc.dockerserve.pojo.body.ModifyImageBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.vo.ImageNameVO;
import com.zumin.dc.dockerserve.pojo.vo.ImageVO;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
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
  @ApiOperation("构建镜像")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "body", value = "构建镜像的信息", dataTypeClass = BuildImageBody.class, required = true),
      @ApiImplicitParam(name = "javaVersion", value = "Java版本", dataTypeClass = String.class)
  })
  public void buildImage(@Valid BuildImageBody body) {
    imageService.build(body);
  }

  @GetMapping("/search")
  @ApiOperation("根据名称搜索共享的应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public List<ImageNameVO> searchByName(@RequestParam(value = "name") String name) {
    return ConvertUtils.convert(imageService.listShareOrUserIdByName(SecurityUtils.getUserId(), name), imageConvert::convertToNameVO);
  }

  @GetMapping("/list")
  @ApiOperation("根据名称查询当前用户的应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public Page<ImageVO> listByCurrentUser(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> imageService.listByUserIdAndName(SecurityUtils.getUserId(), name), imageConvert::convertToVO);
  }

  @GetMapping("/listShare")
  @ApiOperation("根据名称查询共享的应用")
  @ApiImplicitParam(name = "name", value = "应用名称", dataTypeClass = String.class, required = true)
  public Page<ImageVO> listShare(@RequestParam(value = "name", required = false) String name) {
    return getPage(() -> imageService.listByShareAndName(true, name), imageConvert::convertToVO);
  }

  @PostMapping("/modify")
  @ApiOperation("修改应用信息")
  @ApiImplicitParam(name = "body", value = "修改的应用信息", dataTypeClass = ModifyImageBody.class, required = true)
  public void modify(@RequestBody @Valid ModifyImageBody body) {
    ImageEntity entity = imageService.getById(body.getId());
    if (entity == null || !DockerServeUtils.checkAccess(entity)) {
      throw new ImageException(DockerServeStatusCode.IMAGE_UNAUTHORIZED_ACCESS);
    }
    imageService.updateById(PublicUtils.selectiveAssign(body, entity));
  }
}
