package com.vincent.demo.configclient;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RefreshScope
public class GitController {

  private final GitConfig gitConfig;
  private final GitAutoRefreshConfig gitAutoRefreshConfig;
  private final RequestService requestService;

  @GetMapping("show")
  public Object show() {
    return gitConfig;
  }

  @GetMapping("autoShow")
  public Object autoShow() {
    return gitAutoRefreshConfig;
  }

  @PostMapping("refresh")
  public String refresh() {
    requestService.okhttpPost();
    return "SUCCESS";
  }
}
