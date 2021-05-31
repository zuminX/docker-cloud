package com.zumin.dc.dockerserve.dockerfile.processor;

import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import java.io.IOException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.NoArgsConstructor;

/**
 * 处理Jar的DockerFile处理器
 */
@NoArgsConstructor
public class DockerFileJarProcessor implements DockerFileProcessor {

  private static final Set<String> SUPPORT_VERSION = Set.of("6", "7", "8", "9", "10", "11");
  private static final String DEFAULT_VERSION = "11";
  private String version;

  /**
   * 指定Java版本
   *
   * @param version Java版本
   */
  public DockerFileJarProcessor(String version) {
    this.version = version;
  }

  /**
   * 替换DockerFile中的版本信息
   *
   * @param info    构建对象的文件信息
   * @param content DockerFile文件内容
   * @return 处理后的DockerFile内容
   */
  @Override
  public String process(FileSaveInfo info, String content) {
    return content.replace("${version}", chooseJavaVersion(info.getPath()));
  }

  /**
   * 选择Java版本
   *
   * @param path Jar文件路径
   * @return Java版本
   */
  private String chooseJavaVersion(String path) {
    // 尝试使用用户指定的Java版本
    version = formatJavaVersion(version);
    if (SUPPORT_VERSION.contains(formatJavaVersion(version))) {
      return version;
    }
    // 若用户指定的Java版本不合法，则尝试从Jar中的Manifest文件中获取
    String javaVersion = formatJavaVersion(getJavaVersionByJar(path));
    // 若都无法得到合法的Java版本，则使用默认的Java版本
    return SUPPORT_VERSION.contains(javaVersion) ? javaVersion : DEFAULT_VERSION;
  }

  /**
   * 格式化Java版本号
   *
   * @param javaVersion Java版本号
   * @return 格式化后的Java版本号，若无法格式化则返回null
   */
  private String formatJavaVersion(String javaVersion) {
    if (StrUtil.isBlank(javaVersion)) {
      return null;
    }
    int index = javaVersion.indexOf('.');
    if (index == -1) {
      // 该版本字符串已格式化
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

  /**
   * 读取Jar文件中的Manifest文件，获取Build-Jdk-Spec属性的值作为Java版本
   *
   * @param path Jar文件路径
   * @return Java版本
   */
  private String getJavaVersionByJar(String path) {
    //TODO 通过读取Class文件的第5-8个字节可获取该文件的版本号，从而得到Java版本
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
