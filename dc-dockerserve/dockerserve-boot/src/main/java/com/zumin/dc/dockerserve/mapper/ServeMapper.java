package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 服务类持久层
 */
@Mapper
public interface ServeMapper extends BaseMapper<ServeEntity> {

  /**
   * 根据服务的名称获取共享的或指定用户的服务
   *
   * @param userId 用户ID
   * @param name   服务名称
   * @return 服务列表
   */
  List<ServeEntity> selectByUserIdOrShareAndNameLike(@Param("userId") Long userId, @Param("name") String name);
}