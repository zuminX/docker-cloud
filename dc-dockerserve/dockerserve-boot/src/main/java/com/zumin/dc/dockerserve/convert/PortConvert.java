package com.zumin.dc.dockerserve.convert;

import com.github.dockerjava.api.model.ContainerPort;
import com.zumin.dc.dockerserve.pojo.vo.PortVO;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 端口对象转换器
 */
@Mapper
public interface PortConvert {

  @Mapping(target = "innerPort", source = "privatePort")
  @Mapping(target = "exportPort", source = "publicPort")
  PortVO convert(ContainerPort port);

  Set<PortVO> convert(ContainerPort[] ports);
}
