package com.zumin.dc.common.core.pojo;

import cn.hutool.core.lang.Pair;
import com.zumin.dc.common.core.utils.ConvertUtils;
import java.util.LinkedList;
import java.util.List;

public class CommandString {

  private final String name;
  private final List<Pair<String, String>> options;

  public CommandString(String name, List<Pair<String, String>> options) {
    this.name = name;
    this.options = options;
  }

  public static CommandStringBuilder builder(String name) {
    return new CommandStringBuilder(name);
  }

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

  public static class CommandStringBuilder {

    private final List<Pair<String, String>> options;
    private final String name;

    CommandStringBuilder(String name) {
      this.name = name;
      this.options = new LinkedList<>();
    }

    public CommandStringBuilder option(String name, String value) {
      this.options.add(Pair.of(name, value));
      return this;
    }

    public CommandStringBuilder option(String value) {
      return this.option(null, value);
    }

    public CommandString build() {
      return new CommandString(name, options);
    }
  }
}