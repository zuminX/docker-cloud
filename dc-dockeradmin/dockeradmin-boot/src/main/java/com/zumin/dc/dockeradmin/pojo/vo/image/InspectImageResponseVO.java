package com.zumin.dc.dockeradmin.pojo.vo.image;

import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerConfigVO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectImageResponseVO {

  private String arch;
  private String author;
  private String comment;
  private ContainerConfigVO config;
  private String container;
  private ContainerConfigVO containerConfig;
  private String created;
  private String dockerVersion;
  private String id;
  private String os;
  private String osVersion;
  private String parent;
  private Long size;
  private List<String> repoTags;
  private List<String> repoDigests;
  private Long virtualSize;
  private GraphDriverVO graphDriver;
  private RootFSVO rootFS;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GraphDriverVO {

    private String name;
    private GraphDataVO data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraphDataVO {

      private String rootDir;
      private String deviceId;
      private String deviceName;
      private String deviceSize;
      private String dir;
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RootFSVO {

    private String type;
    private List<String> layers;
  }
}
