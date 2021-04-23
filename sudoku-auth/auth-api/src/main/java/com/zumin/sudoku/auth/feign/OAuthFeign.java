package com.zumin.sudoku.auth.feign;


import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.result.CommonResult;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sudoku-auth", contextId = "oauth")
public interface OAuthFeign {

  @PostMapping(value = "/oauth/token")
  CommonResult<OAuth2TokenDTO> postAccessToken(@RequestParam Map<String, String> parameters);
}