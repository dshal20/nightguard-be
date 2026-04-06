package com.nightguard.api.notification;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @PostMapping("/subscribe")
  public ResponseEntity<List<NotificationSubscription>> subscribe(
      @RequestBody SubscribeRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(notificationService.subscribe(request, authentication.getName()));
  }
}
