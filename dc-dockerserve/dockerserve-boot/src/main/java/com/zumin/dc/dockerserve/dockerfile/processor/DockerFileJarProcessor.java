package com.zumin.dc.dockerserve.dockerfile.processor;

import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import java.io.IOException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DockerFileJarProcessor implements DockerFileProcessor {

  private static final Set<String> SUPPORT_VERSION = Set.of("6", "7", "8", "9", "10", "11");
  private static final String DEFAULT_VERSION = "11";
  private String version;

  public DockerFileJarProcessor(String version) {
    this.version = version;
  }

  @Override
  public String process(FileSaveInfo info, String fileString) {
    return fileString.replace("${version}", chooseJavaVersion(info.getPath()));
  }

  private String chooseJavaVersion(String path) {
    version = formatJavaVersion(version);
    if (SUPPORT_VERSION.contains(formatJavaVersion(version))) {
      return version;
    }
    String javaVersion = formatJavaVersion(getJarJavaVersion(path));
    return SUPPORT_VERSION.contains(javaVersion) ? javaVersion : DEFAULT_VERSION;
  }

  /**
   * 格式化Java版本号
   *
   * @param javaVersion Java版本号
   * @return 规范化后的Java版本号，若无法规范化则返回null
   */
  private String formatJavaVersion(String javaVersion) {
    if (StrUtil.isBlank(javaVersion)) {
      return null;
    }
    int index = javaVersion.indexOf('.');
    if (index == -1) {
      // 该版本字符串已规范化
      return javaVersion;
    }
    if (index != 1 || javaVersion.charAt(0) != '1') {
      // 若存在版本的第一部分，则其不可能为空且必须为'1'
      return null;
    }
    // 去除第一部分
    javaVersion = javaVersion.substring(2);
    // 只保留第二部分
    return StrUtil.subBefore(javaVersion, ".", false);
  }

  private String getJarJavaVersion(String path) {
    try {
      JarFile file = new JarFile(path);
      Manifest manifest = file.getManifest();
      Attributes mainAttributes = manifest.getMainAttributes();
      return mainAttributes.getValue("Build-Jdk-Spec");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
