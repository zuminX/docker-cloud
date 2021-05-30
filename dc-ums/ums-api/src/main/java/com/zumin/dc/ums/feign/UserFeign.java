package com.zumin.dc.ums.feign;

import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.ums.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dc-ums", contextId = "ums")
public interface UserFeign {

  @GetMapping("/user/username/{username}")
  CommonResult<UserDTO> getUserByUsername(@PathVariable String username);
}
