package com.zumin.dc.dockerserve.dockerfile;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import com.zumin.dc.common.core.utils.file.FileUtils;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import java.io.File;

/**
 * DockerFile构建器
 */
public class DockerFileBuilder {

  /**
   * 构建DockerFile
   *
   * @param info      构建对象的文件信息
   * @param processor 构建内容处理器
   * @return DockerFile文件对象
   */
  public static File build(FileSaveInfo info, DockerFileProcessor processor) {
    String dockerFileContent = new ClassPathResource(getDockerFilePath(info.getType())).readUtf8Str();
    dockerFileContent = dockerFileContent.replace("${name}", info.getName());
    dockerFileContent = processor.process(info, dockerFileContent);
    return FileUtil.writeUtf8String(dockerFileContent, FileUtils.connect(info.getDirectory(), "Dockerfile"));
  }

  /**
   * 获取DockerFile路径
   *
   * @param fileType 构建对象的类型
   * @return DockerFile路径
   */
  private static String getDockerFilePath(String fileType) {
    return FileUtils.connect("dockerFile", fileType, "Dockerfile");
  }
}
