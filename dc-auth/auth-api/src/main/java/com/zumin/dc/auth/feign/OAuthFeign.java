package com.zumin.dc.auth.feign;


import com.zumin.dc.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.dc.common.core.result.CommonResult;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dc-auth", contextId = "oauth")
public interface OAuthFeign {

  @PostMapping(value = "/oauth/token")
  CommonResult<OAuth2TokenDTO> postAccessToken(@RequestParam Map<String, String> parameters);
}
