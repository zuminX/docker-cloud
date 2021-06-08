package com.zumin.dc.dockeradmin.controller;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ConfigConvert;
import com.zumin.dc.dockeradmin.pojo.vo.ConfigVO;
import com.zumin.dc.dockeradmin.service.ConfigService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/config", tags = "Docker配置API接口")
public class ConfigController {

  private final ConfigService configService;
  private final ConfigConvert configConvert;

  @GetMapping("/list")
  @ApiOperation("获取Docker所有的配置信息")
  public List<ConfigVO> listConfig() {
    return ConvertUtils.convert(configService.list(), configConvert::convert);
  }

  @GetMapping("/delete")
  @ApiOperation("删除指定的配置")
  @ApiImplicitParam(name = "configId", value = "配置ID", dataTypeClass = String.class, required = true)
  public void deleteConfig(@RequestParam("configId") String configId) {
    configService.delete(configId);
  }
}