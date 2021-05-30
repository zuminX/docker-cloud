package com.zumin.dc.dockerserve.dockerfile;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import com.zumin.dc.common.core.utils.file.FileUtils;
import com.zumin.dc.dockerserve.dockerfile.processor.DockerFileProcessor;
import java.io.File;
import java.util.List;

public class DockerFileBuilder {

  public static File build(FileSaveInfo info, DockerFileProcessor processor) {
    String dockerFileContent = new ClassPathResource(getDockerFilePath(info.getType())).readUtf8Str();
    dockerFileContent = dockerFileContent.replace("${name}", info.getName());
    dockerFileContent = processor.process(info, dockerFileContent);
    return FileUtil.writeUtf8String(dockerFileContent, FileUtils.connect(info.getDirectory(), "Dockerfile"));
  }

  private static String getDockerFilePath(String fileType) {
    return FileUtils.connect("dockerFile", fileType, "Dockerfile");
  }
}
