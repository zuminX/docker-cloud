package com.zumin.dc.dockerserve.pojo.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DockerCompose的映射类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComposeBO {

  /**
   * Compose版本
   */
  private String version;
  /**
   * Compose服务
   */
  private Map<String, ComposeServiceBO> services;

  public static ComposeBOBuilder builder() {
    return new ComposeBOBuilder();
  }

  public static class ComposeBOBuilder {

    private final Map<String, ComposeServiceBO> services;
    private String version;

    ComposeBOBuilder() {
      this.version = "3";
      this.services = new LinkedHashMap<>();
    }

    /**
     * 设置Docker版本
     *
     * @param version 版本
     * @return 当前构建器对象
     */
    public ComposeBOBuilder version(String version) {
      this.version = version;
      return this;
    }

    /**
     * 添加Compose服务
     *
     * @param name    服务名称
     * @param service 服务
     * @return 当前构建器对象
     */
    public ComposeBOBuilder service(String name, ComposeServiceBO service) {
      this.services.put(name, service);
      return this;
    }

    public ComposeBO build() {
      return new ComposeBO(version, services);
    }

    public String toString() {
      return "ComposeInfo.ComposeInfoBuilder(version=" + this.version + ", services=" + this.services + ")";
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ComposeServiceBO {

    /**
     * 镜像标识
     */
    private String image;
    /**
     * 容器名称
     */
    private String container_name;
    /**
     * 环境
     */
    private List<String> environment;
    /**
     * 端口号
     */
    private List<String> ports;
    /**
     * 挂载卷
     */
    private List<String> volumes;
    /**
     * 外部链接
     */
    private Collection<String> external_links;

    public static ComposeServiceBOBuilder builder() {
      return new ComposeServiceBOBuilder();
    }

    public static class ComposeServiceBOBuilder {

      private String image;
      private String container_name;
      private List<String> environment;
      private List<String> ports;
      private List<String> volumes;
      private Collection<String> external_links;

      ComposeServiceBOBuilder() {
        this.environment = new ArrayList<>();
        this.ports = new ArrayList<>();
        this.volumes = new ArrayList<>();
        this.external_links = new ArrayList<>();
      }

      public ComposeServiceBOBuilder image(String image) {
        this.image = image;
        return this;
      }

      public ComposeServiceBOBuilder container_name(String container_name) {
        this.container_name = container_name;
        return this;
      }

      public ComposeServiceBOBuilder environment(List<String> environment) {
        this.environment = environment;
        return this;
      }

      public ComposeServiceBOBuilder ports(List<String> ports) {
        this.ports = ports;
        return this;
      }

      public ComposeServiceBOBuilder volumes(List<String> volumes) {
        this.volumes = volumes;
        return this;
      }

      public ComposeServiceBOBuilder external_links(Collection<String> external_links) {
        this.external_links = external_links;
        return this;
      }

      public ComposeServiceBO build() {
        return new ComposeServiceBO(image, container_name, environment, ports, volumes, external_links);
      }

      public String toString() {
        return "ComposeBO.ComposeServiceBO.ComposeServiceBOBuilder(image=" + this.image + ", container_name=" + this.container_name + ", environment="
            + this.environment + ", ports=" + this.ports + ", volumes=" + this.volumes + ", external_links=" + this.external_links + ")";
      }
    }
  }

}
