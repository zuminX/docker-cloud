package com.zumin.dc.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.zumin.dc.common.web.annotation.ComRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@ComRestController(tags = "获取公钥接口")
public class PublicKeyController {

  private final KeyPair keyPair;

  @ApiOperation("获取公钥")
  @GetMapping("/getPublicKey")
  public Map<String, Object> loadPublicKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }

}
