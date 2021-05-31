package com.zumin.dc.dockerserve.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.dc.dockerserve.mapper.ServeDependMapper;
import com.zumin.dc.dockerserve.pojo.entity.ServeDependEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 服务依赖服务类
 */
@Service
@RequiredArgsConstructor
public class ServeDependService extends ServiceImpl<ServeDependMapper, ServeDependEntity> {

}


