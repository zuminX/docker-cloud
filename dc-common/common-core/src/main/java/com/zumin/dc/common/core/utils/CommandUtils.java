package com.zumin.dc.common.core.utils;

import cn.hutool.core.util.RuntimeUtil;
import com.zumin.dc.common.core.pojo.CommandString;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class CommandUtils {

  /**
   * 执行命令
   *
   * @param commandString 命令字符串
   * @return 执行结果列表
   */
  public List<String> execute(CommandString commandString) {
    List<String> result = RuntimeUtil.execForLines(commandString.getCommand());
    log.debug(String.valueOf(result));
    return result;
  }
}
