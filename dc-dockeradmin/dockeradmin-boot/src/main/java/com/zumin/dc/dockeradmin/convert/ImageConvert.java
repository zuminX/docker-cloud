package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.command.InspectImageResponse;
import com.zumin.dc.dockeradmin.pojo.vo.image.InspectImageResponseVO;
import org.mapstruct.Mapper;

@Mapper
public interface ImageConvert {

  InspectImageResponseVO convert(InspectImageResponse response);
}
