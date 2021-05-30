package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import org.mapstruct.Mapper;

@Mapper
public interface ServeConvert {

  ServeNameVO convert(ServeEntity entity);
}
