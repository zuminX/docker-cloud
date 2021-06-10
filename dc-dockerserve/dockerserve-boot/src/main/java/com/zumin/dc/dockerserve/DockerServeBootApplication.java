package com.zumin.dc.dockerserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableDiscoveryClient
@SpringBootApplication
public class DockerServeBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DockerServeBootApplication.class, args);
  }

}
