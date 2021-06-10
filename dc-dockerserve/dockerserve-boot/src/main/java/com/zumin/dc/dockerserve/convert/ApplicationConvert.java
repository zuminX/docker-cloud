package com.zumin.dc.dockerserve.convert;

import com.zumin.dc.dockerserve.pojo.body.ApplicationSaveBody;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.vo.ApplicationDetailVO;
import com.zumin.dc.dockerserve.pojo.vo.ServeDetailVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 应用类的转换器
 */
@Mapper
public interface ApplicationConvert {

  @Mapping(target = "updateTime", ignore = true)
  @Mapping(target = "createTime", ignore = true)
  ApplicationEntity convertToEntity(ApplicationSaveBody body, Long userId);

  ApplicationDetailVO convertToDetail(ApplicationEntity entity, List<ServeDetailVO> serveList);
}
