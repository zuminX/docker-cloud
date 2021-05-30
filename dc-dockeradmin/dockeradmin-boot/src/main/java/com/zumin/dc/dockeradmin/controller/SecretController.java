package com.zumin.dc.dockeradmin.controller;

import com.github.dockerjava.api.model.Secret;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.dockeradmin.service.SecretService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@ComRestController(path = "/secret", tags = "Docker机密API接口")
public class SecretController {

  private final SecretService secretService;

  @GetMapping("/list")
  public List<Secret> listOfAllSecret() {
    return secretService.listOfAllSecret();
  }

  @DeleteMapping("/{secretId}")
  public void deleteSecret(@PathVariable String secretId) {
    secretService.deleteSecret(secretId);
  }
}