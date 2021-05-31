package com.zumin.dc.dockerserve.command;

import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.pojo.CommandString;
import com.zumin.dc.common.core.utils.CommandUtils;
import com.zumin.dc.dockerserve.command.parameter.BuildImageParameter;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ImageException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Docker镜像命令
 */
@Slf4j
@UtilityClass
public class ImageCommand {

  /**
   * 构建镜像
   *
   * @param parameter 构建参数
   * @return Docker镜像ID
   */
  public String build(BuildImageParameter parameter) {
    CommandString command = CommandString.builder("docker build").option("-t", getImageName(parameter)).option(parameter.getDirectory()).build();
    List<String> result = CommandUtils.execute(command);
    String successfully = "Successfully built";
    List<String> imageIdStr = result.stream()
        .filter(StrUtil::isNotBlank)
        .filter(str -> StrUtil.startWith(str, successfully))
        .collect(Collectors.toList());
    if (imageIdStr.size() != 1) {
      throw new ImageException(DockerServeStatusCode.IMAGE_BUILD_ERROR);
    }
    return StrUtil.trim(StrUtil.removePrefix(imageIdStr.get(0), successfully));
  }

  /**
   * 获取镜像名称
   *
   * @param parameter 构建镜像参数
   * @return 名称
   */
  private String getImageName(BuildImageParameter parameter) {
    return parameter.getName() + ":" + parameter.getVersion();
  }
}
