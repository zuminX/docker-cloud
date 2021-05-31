package com.zumin.dc.ums.controller;

import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.convert.UserConvert;
import com.zumin.dc.ums.enums.UmsStatusCode;
import com.zumin.dc.ums.exception.UserException;
import com.zumin.dc.ums.pojo.vo.UserVO;
import com.zumin.dc.ums.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

  @PostMapping("/listNickname")
  @ApiOperation("批量获取用户的昵称")
  public List<String> getNicknameById(@RequestBody List<Long> userIdList) {
    return userService.listNicknameById(userIdList);
  }

  @GetMapping("/nickname")
  @ApiOperation("获取用户的昵称")
  public CommonResult<String> getNicknameById(@RequestParam("userId") Long userId) {
    return CommonResult.success(userService.getNicknameById(userId));
  }

}
