package com.zumin.dc.dockerserve.command;

import cn.hutool.core.util.RuntimeUtil;
import com.zumin.dc.common.core.pojo.CommandString;
import com.zumin.dc.common.core.pojo.CommandString.CommandStringBuilder;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DockerComposeCommand {

  @Getter
  private final String path;

  /**
   * 启动Compose
   */
  public void up() {
    //TODO 如果镜像不存在，则会长时间阻塞
    exec("up", "-d");
  }

  /**
   * 停止Compose
   */
  public void stop() {
    exec("stop");
  }

  /**
   * 查看容器信息
   *
   * @return 容器ID列表
   */
  public List<String> psContainer() {
    return exec("ps", "-q");
  }

  /**
   * 查看服务信息
   *
   * @return 服务标识列表
   */
  public List<String> psServe() {
    return exec("ps", "--services");
  }

  private List<String> exec(String... options) {
    CommandStringBuilder builder = CommandString.builder("docker-compose").option("-f", path);
    Arrays.stream(options).forEach(builder::option);
    String command = builder.build().getCommand();
    List<String> result = RuntimeUtil.execForLines(command);
    log.debug(String.valueOf(result));
    return result;
  }

}
