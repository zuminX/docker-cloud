package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.model.Config;
import com.zumin.dc.dockeradmin.pojo.vo.ConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConfigConvert {

  @Mapping(target = "spec", source = "spec.name")
  @Mapping(target = "version", source = "version.index")
  ConfigVO convert(Config config);
}
