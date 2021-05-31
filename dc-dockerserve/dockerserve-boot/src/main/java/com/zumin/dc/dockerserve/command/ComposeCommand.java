package com.zumin.dc.dockerserve.command;

import com.zumin.dc.common.core.pojo.CommandString;
import com.zumin.dc.common.core.pojo.CommandString.CommandStringBuilder;
import com.zumin.dc.common.core.utils.CommandUtils;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * DockerCompose命令
 */
@Slf4j
@AllArgsConstructor
public class ComposeCommand {

  /**
   * Compose文件路径
   */
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

  /**
   * 执行命令
   *
   * @param options 选项
   * @return 执行结果列表
   */
  private List<String> exec(String... options) {
    CommandStringBuilder builder = CommandString.builder("docker-compose").option("-f", path);
    Arrays.stream(options).forEach(builder::option);
    return CommandUtils.execute(builder.build());
  }

}
