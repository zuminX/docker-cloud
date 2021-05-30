package com.zumin.dc.common.core.template;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 自定义配置文件加载模板
 */
public abstract class EnvironmentProcessorTemplate implements EnvironmentPostProcessor {

  private final YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();

  private final PropertiesPropertySourceLoader propertiesLoader = new PropertiesPropertySourceLoader();

  /**
   * 获取配置文件名
   *
   * @return 配置文件名数组
   */
  public abstract String[] getProfiles();

  /**
   * 后处理配置环境
   *
   * @param environment 配置环境
   * @param application Spring应用
   */
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    Arrays.stream(getProfiles()).map(ClassPathResource::new).forEach(resource -> environment.getPropertySources().addLast(loadProfiles(resource)));
  }

  /**
   * 加载单个配置文件
   *
   * @param resource 资源
   * @return 配置源
   */
  private PropertySource<?> loadProfiles(Resource resource) {
    if (!resource.exists()) {
      throw new IllegalArgumentException("资源" + resource + "不存在");
    }
    try {
      return getSourceLoaderByFileType(resource.getFile()).load(resource.getFilename(), resource).get(0);
    } catch (IOException ex) {
      throw new IllegalStateException("加载配置文件失败" + resource, ex);
    }
  }

  /**
   * 根据配置文件的类型选择对应的加载器
   *
   * @param file 配置文件
   * @return 属性加载器
   */
  private PropertySourceLoader getSourceLoaderByFileType(File file) {
    String suffix = FileUtil.getSuffix(file);
    return suffix.equals("yaml") || suffix.equals("yml") ? yamlLoader : propertiesLoader;
  }
}
