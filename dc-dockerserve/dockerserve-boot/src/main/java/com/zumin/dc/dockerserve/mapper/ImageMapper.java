package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 镜像类持久层
 */
@Mapper
public interface ImageMapper extends BaseMapper<ImageEntity> {

  /**
   * 根据镜像的名称获取共享的或指定用户的镜像
   *
   * @param userId   用户ID
   * @param likeName 镜像名称
   * @return 镜像列表
   */
  List<ImageEntity> selectByShareOrUserIdAndNameLike(@Param("userId") Long userId, @Param("likeName") String likeName);
}