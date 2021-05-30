package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import java.util.List;import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServeMapper extends BaseMapper<ServeEntity> {

  List<ServeEntity> selectByUserIdOrShareAndNameLike(@Param("userId") Long userId, @Param("name") String name);
}