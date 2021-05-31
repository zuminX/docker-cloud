package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import org.mapstruct.Mapper;

/**
 * 服务对象转换器
 */
@Mapper
public interface ServeConvert {

  ServeNameVO convert(ServeEntity entity);
}
