package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImageMapper extends BaseMapper<ImageEntity> {

  List<ImageEntity> selectByShareOrUserIdAndNameLike(@Param("userId") Long userId, @Param("likeName") String likeName);
}