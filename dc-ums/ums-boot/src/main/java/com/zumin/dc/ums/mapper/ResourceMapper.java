package com.zumin.dc.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.dc.ums.pojo.entity.ResourceEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper extends BaseMapper<ResourceEntity> {

  List<ResourceEntity> listWithRoles();
}