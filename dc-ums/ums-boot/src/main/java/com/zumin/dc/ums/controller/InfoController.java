package com.zumin.dc.ums.controller;

import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.convert.UserConvert;
import com.zumin.dc.ums.enums.UmsStatusCode;
import com.zumin.dc.ums.exception.UserException;
import com.zumin.dc.ums.pojo.vo.UserVO;
import com.zumin.dc.ums.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/info", tags = "用户信息API接口")
public class InfoController {

  private final UserService userService;
  private final UserConvert userConvert;

  @GetMapping("/basic")
  @ApiOperation("获取当前用户的基本信息")
  public UserVO basicInfo() {
    Long userId = SecurityUtils.getUserId();
    if (userId == null) {
      throw new UserException(UmsStatusCode.USER_NOT_LOGIN);
    }
    return userConvert.convert(userService.getUserWithRoleById(userId));
  }
}
