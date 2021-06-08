package com.zumin.dc.dockerserve.convert;

import cn.hutool.core.util.StrUtil;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeLinkDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeVO;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 服务类的转换器
 */
@Mapper
public interface ServeConvert {

  ServeNameVO convertToNameVO(ServeEntity entity);

  @Mapping(target = "portList", source = "port")
  ServeVO convertToVO(ServeEntity entity, String state);

  @Mapping(target = "imageId", source = "imageEntity.id")
  @Mapping(target = "imageName", source = "imageEntity.name")
  @Mapping(target = "portList", source = "serveEntity.port")
  ServeDetailVO convertToDetailVO(ServeEntity serveEntity, ImageEntity imageEntity, List<ServeLinkDetailVO> linkServeList);

  default Set<Integer> splitPort(String port) {
    return Arrays.stream(StrUtil.split(port, ";")).map(Integer::parseInt).collect(Collectors.toSet());
  }
}
