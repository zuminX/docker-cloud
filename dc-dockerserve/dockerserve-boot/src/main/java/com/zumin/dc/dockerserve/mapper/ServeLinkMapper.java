package com.zumin.dc.dockerserve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.dockerserve.pojo.entity.ServeLinkEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务链接类持久层
 */
@Mapper
public interface ServeLinkMapper extends BaseMapper<ServeLinkEntity> {

}