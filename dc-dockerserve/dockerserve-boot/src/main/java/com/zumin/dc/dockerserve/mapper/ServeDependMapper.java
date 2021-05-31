package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ServeDependEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务依赖类持久层
 */
@Mapper
public interface ServeDependMapper extends BaseMapper<ServeDependEntity> {

}