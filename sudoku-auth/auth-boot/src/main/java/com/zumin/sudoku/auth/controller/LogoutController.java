package com.zumin.sudoku.auth.controller;

import cn.hutool.json.JSONObject;
import com.zumin.sudoku.auth.constants.AuthRedisKey;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.common.redis.utils.RedisUtils;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

@RequiredArgsConstructor
@ComRestController(path = "/oauth", tags = "注销")
public class LogoutController {

  private final RedisUtils redisUtils;

  @ApiOperation("登出")
  @DeleteMapping("/logout")
  public CommonResult<?> logout() {
    JSONObject jsonObject = SecurityUtils.getJwtPayload();
    // JWT唯一标识
    String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
    // JWT过期时间戳
    long exp = jsonObject.getLong(AuthConstants.JWT_EXP);
    long currentTimeSeconds = System.currentTimeMillis() / 1000;
    // token已过期，无需加入黑名单
    if (exp >= currentTimeSeconds) {
      redisUtils.set(AuthRedisKey.TOKEN_BLACKLIST_PREFIX + jti, "", (exp - currentTimeSeconds), TimeUnit.SECONDS);
    }
    return CommonResult.success();
  }

}
