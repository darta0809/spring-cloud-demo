package com.vincent.demo.configclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "data")
public class GitAutoRefreshConfig {

  private String env;
  private UserInfo user;

  @Data
  public static class UserInfo {

    private String username;
    private String password;
  }

}
