package com.zumin.dc.auth.feign;

import com.zumin.dc.common.core.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 验证码API远程接口
 */
@FeignClient(name = "dc-auth", contextId = "captcha")
public interface CaptchaFeign {

  /**
   * 检查验证码
   *
   * @param uuid 验证码唯一标识
   * @param code 待验证值
   * @return 验证结果
   */
  @GetMapping("/captcha/check")
  CommonResult<?> checkCaptcha(@RequestParam("uuid") String uuid, @RequestParam("code") String code);
}
