package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.model.Info;
import com.zumin.dc.dockeradmin.pojo.vo.InfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InfoConvert {

  @Mapping(target = "storageDriver", ignore = true)
  @Mapping(target = "memory", source = "memTotal")
  @Mapping(target = "cpus", expression = "java(info.getNCPU())")
  InfoVO convert(Info info);
}
