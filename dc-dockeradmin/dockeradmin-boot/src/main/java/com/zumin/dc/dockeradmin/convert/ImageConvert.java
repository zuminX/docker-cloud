package com.zumin.dc.dockeradmin.convert;

import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.zumin.dc.dockeradmin.pojo.vo.image.ImageVO;
import com.zumin.dc.dockeradmin.pojo.vo.image.InspectImageResponseVO;
import org.mapstruct.Mapper;

/**
 * 镜像类的转换器类
 */
@Mapper
public interface ImageConvert {

  InspectImageResponseVO convert(InspectImageResponse response);

  ImageVO convert(Image image);
}
