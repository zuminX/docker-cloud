package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeLinkEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeLinkDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 服务链接类的转换器
 */
@Mapper
public interface ServeLinkConvert {

  @Mapping(target = "beLinkServeId", source = "serveEntity.id")
  @Mapping(target = "beLinkServeName", source = "serveEntity.name")
  ServeLinkDetailVO convert(ServeLinkEntity serveLinkEntity, ServeEntity serveEntity);
}
