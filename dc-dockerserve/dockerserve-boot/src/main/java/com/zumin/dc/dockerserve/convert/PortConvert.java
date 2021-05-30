package com.zumin.dc.dockerserve.convert;

import com.github.dockerjava.api.model.ContainerPort;
import com.zumin.dc.dockerserve.pojo.vo.PortVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PortConvert {

  @Mapping(target = "innerPort", source = "privatePort")
  @Mapping(target = "exportPort", source = "publicPort")
  PortVO convert(ContainerPort port);

  List<PortVO> convert(ContainerPort[] ports);
}
