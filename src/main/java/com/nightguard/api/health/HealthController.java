package com.nightguard.api.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
  @GetMapping("/health")
  public String getMethodName() {
      return new String("Online");
  }
}
