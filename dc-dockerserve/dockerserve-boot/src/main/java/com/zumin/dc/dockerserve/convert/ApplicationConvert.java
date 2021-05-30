package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.body.CreateApplicationBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationConvert {

  @Mapping(target = "updateTime", ignore = true)
  @Mapping(target = "createTime", ignore = true)
  @Mapping(target = "id", ignore = true)
  ApplicationEntity convert(CreateApplicationBody body, Long userId);
}
