package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用类持久层
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<ApplicationEntity> {

}