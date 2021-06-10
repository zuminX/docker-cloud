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

  private String version;
  private Map<String, ComposeServiceBO> services;

  public static ComposeBOBuilder builder() {
    return new ComposeBOBuilder();
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ComposeServiceBO {

    private String image;
    private String container_name;
    private List<String> environment;
    private List<String> ports;
    private List<String> volumes;
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

  public static class ComposeBOBuilder {

    private final Map<String, ComposeServiceBO> services;
    private String version;

    ComposeBOBuilder() {
      this.version = "3";
      this.services = new LinkedHashMap<>();
    }

    public ComposeBOBuilder version(String version) {
      this.version = version;
      return this;
    }

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
}
