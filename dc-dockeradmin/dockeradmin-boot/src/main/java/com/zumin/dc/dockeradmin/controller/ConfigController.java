package com.zumin.dc.dockeradmin.controller;

import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.convert.ConfigConvert;
import com.zumin.dc.dockeradmin.pojo.vo.ConfigVO;
import com.zumin.dc.dockeradmin.service.ConfigService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@ComRestController(path = "/config", tags = "Docker配置API接口")
public class ConfigController {

  private final ConfigService configService;
  private final ConfigConvert configConvert;

  @GetMapping("/list")
  public List<ConfigVO> listOfAllConfig() {
    return ConvertUtils.convert(configService.listOfAllConfig(), configConvert::convert);
  }

  @DeleteMapping("/{configId}")
  public void deleteConfig(@PathVariable String configId) {
    configService.deleteConfig(configId);
  }
}