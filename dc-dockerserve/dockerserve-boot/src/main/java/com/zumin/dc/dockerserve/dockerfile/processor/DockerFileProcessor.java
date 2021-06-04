package com.zumin.dc.dockerserve.dockerfile.processor;

import com.zumin.dc.common.core.pojo.FileSaveInfo;

/**
 * DockerFile处理器
 */
public interface DockerFileProcessor {

  /**
   * 默认处理器，即不做任何处理
   */
  DockerFileProcessor DEFAULT = (info, dockerFileContent) -> dockerFileContent;

  /**
   * 处理DockerFile内容
   *
   * @param info    构建对象的文件信息
   * @param content DockerFile文件内容
   * @return 处理后的DockerFile内容
   */
  String process(FileSaveInfo info, String content);
}
