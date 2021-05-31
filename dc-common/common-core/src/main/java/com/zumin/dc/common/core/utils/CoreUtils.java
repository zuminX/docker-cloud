package com.zumin.dc.common.core.utils;

import cn.hutool.core.util.ClassUtil;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 核心工具类
 */
@Component
@RequiredArgsConstructor
public class CoreUtils {

  private static String PROJECT_PACKAGE_NAME;

  private final Environment env;

  /**
   * 获取项目的所有Class
   *
   * @return Class集合
   */
  public Set<Class<?>> getClasses() {
    return ClassUtil.scanPackage(PROJECT_PACKAGE_NAME);
  }

  /**
   * 读取配置
   */
  @PostConstruct
  public void readConfig() {
    PROJECT_PACKAGE_NAME = env.getProperty("common.core.projectPackage", "com.zumin.dc");
  }
}
