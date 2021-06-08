package com.zumin.dc.common.core.pojo;

import cn.hutool.core.lang.Pair;
import com.zumin.dc.common.core.utils.ConvertUtils;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * 命令字符串类
 */
@AllArgsConstructor
public class CommandString {

  private final String name;
  private final List<Pair<String, String>> options;

  /**
   * 获取命令字符串类的构建器类
   *
   * @param name 命令名
   * @return 命令字符串类的构建器
   */
  public static CommandStringBuilder builder(String name) {
    return new CommandStringBuilder(name);
  }

  /**
   * 获取命令
   *
   * @return 命令字符串
   */
  public String getCommand() {
    String optionsString = ConvertUtils.convertAndJoin(options, pair -> {
      String tmp = "";
      if (pair.getKey() != null) {
        tmp += pair.getKey() + " ";
      }
      tmp += pair.getValue();
      return tmp;
    }, " ");
    return name + " " + optionsString;
  }

  /**
   * 命令字符串类的构建器类
   */
  public static class CommandStringBuilder {

    private final List<Pair<String, String>> options;
    private final String name;

    CommandStringBuilder(String name) {
      this.name = name;
      this.options = new LinkedList<>();
    }

    /**
     * 添加命令的参数
     *
     * @param name  参数名
     * @param value 参数值
     * @return 当前的构建器对象
     */
    public CommandStringBuilder option(String name, String value) {
      this.options.add(Pair.of(name, value));
      return this;
    }

    /**
     * 添加命令的参数
     *
     * @param value 参数值
     * @return 当前的构建器对象
     */
    public CommandStringBuilder option(String value) {
      return this.option(null, value);
    }

    /**
     * 使用设置的参数构建命令字符串对象
     *
     * @return 命令字符串对象
     */
    public CommandString build() {
      return new CommandString(name, options);
    }
  }
}