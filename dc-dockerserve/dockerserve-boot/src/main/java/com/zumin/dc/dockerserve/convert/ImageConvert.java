package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.body.BuildImageBody;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.vo.ImageNameVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ImageConvert {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createTime", ignore = true)
  ImageEntity convert(BuildImageBody body, Long userId, String indicate);

  ImageNameVO convert(ImageEntity entity);
}
