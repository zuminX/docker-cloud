package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.body.ImageBuildBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.vo.ImageNameVO;
import com.zumin.dc.dockerserve.pojo.vo.ImageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 镜像类的转换器
 */
@Mapper
public interface ImageConvert {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createTime", ignore = true)
  ImageEntity convert(ImageBuildBody body, Long userId, String indicate);

  ImageNameVO convertToNameVO(ImageEntity entity);

  ImageVO convertToVO(ImageEntity entity);
}
