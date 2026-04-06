package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService notificationService;
  private final UserRepository userRepository;

  public NotificationController(NotificationService notificationService, UserRepository userRepository) {
    this.notificationService = notificationService;
    this.userRepository = userRepository;
  }

  @GetMapping("/venues/{venueId}/subscriptions")
  public List<NotificationSubscription> getSubscriptions(@PathVariable UUID venueId) {
    return notificationService.getSubscriptions(venueId);
  }

  @PostMapping("/venues/{venueId}/subscriptions")
  public List<NotificationSubscription> subscribe(
      @PathVariable UUID venueId,
      @RequestBody SubscribeRequest request) {
    return notificationService.subscribe(venueId, request.getVenueIds());
  }

  @DeleteMapping("/venues/{venueId}/subscriptions/{targetVenueId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unsubscribe(@PathVariable UUID venueId, @PathVariable UUID targetVenueId) {
    notificationService.unsubscribe(venueId, targetVenueId);
  }

  @PostMapping("/register-device")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void registerDevice(
      @RequestBody RegisterDeviceRequest request,
      Authentication authentication) {
    User user = userRepository.findById(authentication.getName())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
    user.setFcmToken(request.getFcmToken());
    userRepository.save(user);
  }
}
