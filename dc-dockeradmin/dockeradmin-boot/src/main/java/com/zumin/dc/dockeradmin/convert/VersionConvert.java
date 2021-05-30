package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.model.Version;
import com.zumin.dc.dockeradmin.pojo.vo.VersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VersionConvert {

  @Mapping(target = "dockerVersion", source = "version")
  VersionVO convert(Version version);
}
