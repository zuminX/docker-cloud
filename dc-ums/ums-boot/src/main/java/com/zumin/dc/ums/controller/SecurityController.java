package com.zumin.dc.ums.controller;


import com.zumin.dc.auth.feign.CaptchaFeign;
import com.zumin.dc.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.ums.convert.UserConvert;
import com.zumin.dc.ums.pojo.body.LoginBody;
import com.zumin.dc.ums.pojo.body.RegisterUserBody;
import com.zumin.dc.ums.pojo.entity.UserEntity;
import com.zumin.dc.ums.pojo.vo.UserVO;
import com.zumin.dc.ums.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@ComRestController(path = "/security", tags = "用户安全API接口")
public class SecurityController {

  private final UserService userService;

  private final CaptchaFeign captchaFeign;

  private final UserConvert userConvert;

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "登录用户表单信息", dataTypeClass = LoginBody.class, required = true)
  public CommonResult<OAuth2TokenDTO> login(@RequestBody @Valid LoginBody loginBody) {
    return userService.login(loginBody);
  }

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserBody.class, required = true)
  public UserVO registerUser(@RequestBody @Valid RegisterUserBody registerUser) {
    captchaFeign.checkCaptcha(registerUser.getUuid(), registerUser.getCode());
    UserEntity userEntity = userService.registerUser(registerUser);
    return userConvert.convert(userEntity);
  }
}
