package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerNetwork;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.Links;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerNetworkVO;
import com.zumin.dc.dockeradmin.pojo.vo.coontainer.ContainerVO;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 容器类的转换器类
 */
@Mapper
public interface ContainerConvert {

  @Mapping(target = "networkSettings", source = "networkSettings.networks")
  @Mapping(target = "hostConfig", source = "hostConfig.networkMode")
  ContainerVO convert(Container container);

  Map<String, ContainerNetworkVO> networkSettings(Map<String, ContainerNetwork> networks);

  default Link[] links(Links links) {
    return links.getLinks();
  }
}
