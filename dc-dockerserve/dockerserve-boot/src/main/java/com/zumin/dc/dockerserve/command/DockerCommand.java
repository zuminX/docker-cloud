package com.zumin.dc.dockerserve.command;


import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.pojo.CommandString;
import com.zumin.dc.dockerserve.command.parameter.BuildImageParameter;
import com.zumin.dc.dockerserve.enums.DockerServeStatusCode;
import com.zumin.dc.dockerserve.exception.ImageException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DockerCommand {

  /**
   * 构建镜像
   * <p>
   * 若成功则返回镜像id
   *
   * @param parameter 构建参数
   * @return 镜像ID
   */
  public String buildImage(BuildImageParameter parameter) {
    String command = CommandString.builder("docker build").option("-t", getImageName(parameter)).option(parameter.getDirectory()).build().getCommand();
    List<String> result = RuntimeUtil.execForLines(command);
    log.debug(String.valueOf(result));
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

  private String getImageName(BuildImageParameter parameter) {
    return parameter.getName() + ":" + parameter.getVersion();
  }
}
