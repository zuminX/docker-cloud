package com.zumin.dc.ums.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "profile")
public class ProfileConfig {

  private AvatarProperties avatar = new AvatarProperties();

  @Data
  public static class AvatarProperties {

    private String dir;
    private long maxSize;
  }
}
