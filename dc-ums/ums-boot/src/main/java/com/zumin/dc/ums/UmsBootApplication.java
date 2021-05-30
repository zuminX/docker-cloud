package com.zumin.dc.ums;

import com.zumin.dc.auth.feign.OAuthFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {OAuthFeign.class})
@SpringBootApplication
public class UmsBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(UmsBootApplication.class, args);
  }

}
