package com.zumin.dc.auth.controller;

import com.zumin.dc.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.dc.auth.service.CaptchaService;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.core.auth.AuthParamName;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@ComRestController(path = "/oauth", tags = "认证中心")
public class OAuthController {

  private final TokenEndpoint tokenEndpoint;

  private final CaptchaService captchaService;

  @ApiOperation(value = "OAuth2认证", notes = "login")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
      @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
      @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
      @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
      @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
      @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),
  })
  @PostMapping("/token")
  public CommonResult<OAuth2TokenDTO> postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters
  ) throws HttpRequestMethodNotSupportedException {
    OAuth2AccessToken oAuth2AccessToken;
    String clientId = SecurityUtils.getAuthClientId();
    switch (clientId) {
      case AuthConstants.WEAPP_CLIENT_ID:  // 微信认证
        throw new UnsupportedOperationException("暂不支持微信");
      default:
        oAuth2AccessToken = handlerForSystem(principal, parameters);
        break;
    }
    OAuth2TokenDTO auth2TokenDTO = OAuth2TokenDTO.builder()
        .token(oAuth2AccessToken.getValue())
        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
        .expiresIn(oAuth2AccessToken.getExpiresIn())
        .build();
    return CommonResult.success(auth2TokenDTO);
  }

  /**
   * 处理系统用户
   *
   * @return Token信息
   */
  private OAuth2AccessToken handlerForSystem(Principal principal, Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
    String uuid = parameters.get(AuthParamName.CAPTCHA_UUID);
    String code = parameters.get(AuthParamName.CAPTCHA_CODE);
    captchaService.checkCaptcha(uuid, code);
    return tokenEndpoint.postAccessToken(principal, parameters).getBody();
  }

}
