package com.vincent.demo.configclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GitController {

  private final GitConfig gitConfig;
  private final GitAutoRefreshConfig gitAutoRefreshConfig;

  @GetMapping(value = "show")
  public Object show() {
    return gitConfig;
  }

  @GetMapping(value = "autoShow")
  public Object autoShow() {
    return gitAutoRefreshConfig;
  }
}
