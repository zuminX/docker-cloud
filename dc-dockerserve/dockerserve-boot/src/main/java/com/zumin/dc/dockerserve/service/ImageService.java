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
import com.zumin.dc.dockerserve.command.ImageCommand;
import com.zumin.dc.dockerserve.command.parameter.BuildImageParameter;
import com.zumin.dc.dockerserve.convert.ImageConvert;
import com.zumin.dc.dockerserve.dockerfile.DockerFileBuilder;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import com.zumin.dc.dockerserve.mapper.ImageMapper;
import com.zumin.dc.dockerserve.pojo.body.BuildImageBody;
import com.zumin.dc.dockerserve.pojo.body.CreateServeBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.utils.DockerServeUtils;
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
   * 构建镜像，并保存信息至数据库
   *
   * @param body      构建镜像的信息
   * @param processor 构建时的处理器
   */
  public void build(BuildImageBody body, DockerFileProcessor processor) {
    FileSaveInfo info = buildSaveInfo(body);
    fileService.save(info);
    String imageIndicate = buildImage(info, body.getVersion(), processor);
    baseMapper.insert(imageConvert.convert(body, SecurityUtils.getUserId(), imageIndicate));
    FileUtil.del(info.getDirectory());
  }

  /**
   * 构建镜像
   *
   * @param info      文件保存信息
   * @param version   镜像的版本
   * @param processor 构建时的处理器
   * @return Docker镜像ID
   */
  private String buildImage(FileSaveInfo info, String version, DockerFileProcessor processor) {
    DockerFileBuilder.build(info, processor);
    BuildImageParameter parameter = BuildImageParameter.builder().directory(info.getDirectory()).name(info.getName()).version(version).build();
    return ImageCommand.build(parameter);
  }

  /**
   * 根据构建镜像生成对应的文件保存信息
   *
   * @param body 构建镜像的信息
   * @return 文件保存信息
   */
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

  /**
   * 检查创建服务所使用的镜像的可访问性
   *
   * @param serveList 服务列表
   * @return 若可访问则返回true，否则返回false
   */
  public boolean checkImageAccess(List<CreateServeBody> serveList) {
    List<Integer> imageIdList = ConvertUtils.convert(serveList, CreateServeBody::getImageId);
    return imageIdList.stream().allMatch(imageId -> DockerServeUtils.checkAccess(getById(imageId)));
  }
}
