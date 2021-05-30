package com.zumin.dc.dockerserve.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.FileException;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import com.zumin.dc.common.core.service.FileService;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.command.DockerCommand;
import com.zumin.dc.dockerserve.command.parameter.BuildImageParameter;
import com.zumin.dc.dockerserve.convert.ImageConvert;
import com.zumin.dc.dockerserve.dockerfile.DockerFileBuilder;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.pojo.body.BuildImageBody;
import com.zumin.dc.dockerserve.pojo.body.CreateApplicationServeBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService extends ServiceImpl<ImageMapper, ImageEntity> {

  private static final List<String> SUPPORTED_BUILD_TYPE = List.of("jar");

  private final FileService fileService;
  private final ImageConvert imageConvert;

  public List<ImageEntity> listByUserIdAndName(Long userId, String name) {
    return list(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getUserId, userId).like(StrUtil.isNotBlank(name), ImageEntity::getName, name));
  }

  public List<ImageEntity> listShareOrUserIdByName(Long userId, String name) {
    return baseMapper.selectByShareOrUserIdAndNameLike(userId, name);
  }

  public List<ImageEntity> listByShareAndName(boolean share, String name) {
    return list(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getShare, share).like(StrUtil.isNotBlank(name), ImageEntity::getName, name));
  }

  public void build(BuildImageBody body, DockerFileProcessor processor) {
    FileSaveInfo info = buildSaveInfo(body);
    fileService.save(info);
    String imageIndicate = buildImage(info, body.getVersion(), processor);
    baseMapper.insert(imageConvert.convert(body, SecurityUtils.getUserId(), imageIndicate));
    FileUtil.del(info.getDirectory());
  }

  private String buildImage(FileSaveInfo info, String version, DockerFileProcessor processor) {
    DockerFileBuilder.build(info, processor);
    BuildImageParameter parameter = BuildImageParameter.builder().directory(info.getDirectory()).name(info.getName()).version(version).build();
    return DockerCommand.buildImage(parameter);
  }

  private FileSaveInfo buildSaveInfo(BuildImageBody body) {
    InputStream inputStream;
    try {
      inputStream = body.getFile().getInputStream();
    } catch (IOException e) {
      throw new FileException(CommonStatusCode.FILE_INACCESSIBLE);
    }
    return FileSaveInfo.builder()
        .fileStream(inputStream)
        .directory(fileService.getTempDirectory())
        .name(body.getName())
        .type(body.getType())
        .allowExtensions(SUPPORTED_BUILD_TYPE)
        .build();
  }

  public boolean checkImageAccess(ImageEntity image) {
    return image.getShare() || image.getUserId().equals(SecurityUtils.getUserId());
  }

  public boolean checkImageAccess(List<CreateApplicationServeBody> serveList) {
    List<Integer> imageIdList = ConvertUtils.convert(serveList, CreateApplicationServeBody::getImageId);
    return imageIdList.stream().allMatch(imageId -> checkImageAccess(getById(imageId)));
  }
}
