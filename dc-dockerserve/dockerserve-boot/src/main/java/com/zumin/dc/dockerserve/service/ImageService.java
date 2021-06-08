package com.zumin.dc.dockerserve.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.FileException;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import com.zumin.dc.common.core.service.FileService;
import com.zumin.dc.common.core.utils.ServletUtils;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.dockerserve.command.ImageCommand;
import com.zumin.dc.dockerserve.command.parameter.BuildImageParameter;
import com.zumin.dc.dockerserve.convert.ImageConvert;
import com.zumin.dc.dockerserve.dockerfile.DockerFileBuilder;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileJarProcessor;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.pojo.body.ImageBuildBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 镜像服务类
 */
@Service
@RequiredArgsConstructor
public class ImageService extends ServiceImpl<ImageMapper, ImageEntity> {

  /**
   * 支持构建镜像的文件类型
   */
  private static final List<String> SUPPORTED_BUILD_TYPE = List.of("jar");

  private final FileService fileService;
  private final ImageConvert imageConvert;

  public ImageEntity getByIndicate(String indicate) {
    return getOne(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getIndicate, indicate));
  }

  /**
   * 根据名称列出当前用户的镜像
   *
   * @param userId 用户ID
   * @param name   镜像名称
   * @return 镜像列表
   */
  public List<ImageEntity> listByUserIdAndName(Long userId, String name) {
    return list(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getUserId, userId).like(StrUtil.isNotBlank(name), ImageEntity::getName, name));
  }

  /**
   * 根据名称列出当前用户或共享的镜像
   *
   * @param userId 用户ID
   * @param name   镜像名称
   * @return 镜像列表
   */
  public List<ImageEntity> listShareOrUserIdByName(Long userId, String name) {
    return baseMapper.selectByShareOrUserIdAndNameLike(userId, name);
  }

  /**
   * 根据名称和是否共享列出镜像
   *
   * @param share 是否共享
   * @param name  镜像名称
   * @return 镜像列表
   */
  public List<ImageEntity> listByShareAndName(boolean share, String name) {
    return list(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getShare, share).like(StrUtil.isNotBlank(name), ImageEntity::getName, name));
  }

  /**
   * 根据名称列出镜像
   *
   * @param name 镜像名称
   * @return 镜像列表
   */
  public List<ImageEntity> listByName(String name) {
    return list(Wrappers.lambdaQuery(ImageEntity.class).eq(StrUtil.isNotBlank(name), ImageEntity::getName, name));
  }

  /**
   * 构建镜像，并保存信息至数据库
   *
   * @param body 构建镜像的信息
   */
  public void build(ImageBuildBody body) {
    FileSaveInfo info = buildSaveInfo(body);
    fileService.save(info);
    String imageIndicate = buildImage(info, body.getVersion());
    baseMapper.insert(imageConvert.convert(body, SecurityUtils.getUserId(), imageIndicate));
    FileUtil.del(info.getDirectory());
  }

  /**
   * 获取用户镜像总数
   *
   * @param userId 用户ID
   * @return 镜像总数
   */
  public int countByUserId(Long userId) {
    return count(Wrappers.lambdaQuery(ImageEntity.class).eq(ImageEntity::getUserId, userId));
  }

  /**
   * 构建镜像
   *
   * @param info    文件保存信息
   * @param version 镜像的版本
   * @return Docker镜像ID
   */
  private String buildImage(FileSaveInfo info, String version) {
    DockerFileBuilder.build(info, selectProcessor(info.getType()));
    BuildImageParameter parameter = BuildImageParameter.builder().directory(info.getDirectory()).name(info.getName()).version(version).build();
    return ImageCommand.build(parameter);
  }

  /**
   * 根据构建镜像生成对应的文件保存信息
   *
   * @param body 构建镜像的信息
   * @return 文件保存信息
   */
  private FileSaveInfo buildSaveInfo(ImageBuildBody body) {
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

  private DockerFileProcessor selectProcessor(String type) {
    if (type.equals("jar")) {
      return new DockerFileJarProcessor(ServletUtils.getParameter("javaVersion"));
    }
    return DockerFileProcessor.DEFAULT;
  }

}
