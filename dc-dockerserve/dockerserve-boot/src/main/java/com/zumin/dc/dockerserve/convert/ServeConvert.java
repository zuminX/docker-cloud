package com.zumin.dc.dockerserve.convert;

import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.dockerserve.pojo.body.ServeSaveBody;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.pojo.vo.ServeDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeLinkDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeNameVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeVO;
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

  @Mapping(target = "portList", source = "entity.port")
  ServeVO convertToVO(ServeEntity entity, String state);

  @Mapping(target = "portList", source = "serveEntity.port")
  ServeDetailVO convertToDetailVO(ServeEntity serveEntity, Long imageId, String imageName, List<ServeLinkDetailVO> linkServeList);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "volume", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "serveIndicate", ignore = true)
  @Mapping(target = "imageIndicate", ignore = true)
  @Mapping(target = "environment", ignore = true)
  @Mapping(target = "port", source = "body.portList")
  ServeEntity convertToEntity(ServeSaveBody body, Long applicationId);

  /**
   * 分割以字符串形式保存的端口号
   *
   * @param port 端口号
   * @return 端口号集合
   */
  default Set<Integer> splitPort(String port) {
    return ConvertUtils.splitAndConvert(port, ";", Integer::parseInt, Collectors.toSet());
  }

  /**
   * 连接端口号
   *
   * @param port 端口号集合
   * @return 以分号连接的端口号字符串
   */
  default String joinPort(Set<Integer> port) {
    return StrUtil.join(";", port);
  }
}
