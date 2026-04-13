package com.nightguard.api.notification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping("/{venueId}/subscriptions")
  public List<NotificationSubscriptionResponse> getSubscriptions(@PathVariable UUID venueId) {
    return notificationService.getSubscriptions(venueId);
  }

  @PostMapping("/{venueId}/subscriptions")
  public List<NotificationSubscriptionResponse> subscribe(
      @PathVariable UUID venueId,
      @RequestBody SubscribeRequest request) {
    return notificationService.subscribe(venueId, request.getVenueIds(), request.getNotificationLevel());
  }

  @PatchMapping("/{venueId}/subscriptions/{targetVenueId}")
  public NotificationSubscriptionResponse updateNotificationLevel(
      @PathVariable UUID venueId,
      @PathVariable UUID targetVenueId,
      @RequestBody UpdateNotificationLevelRequest request) {
    return notificationService.updateNotificationLevel(venueId, targetVenueId, request.getNotificationLevel());
  }

  @DeleteMapping("/{venueId}/subscriptions/{targetVenueId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unsubscribe(@PathVariable UUID venueId, @PathVariable UUID targetVenueId) {
    notificationService.unsubscribe(venueId, targetVenueId);
  }

  @GetMapping("/{venueId}/activity")
  public List<NotificationActivityResponse> getActivity(
      @PathVariable UUID venueId,
      @RequestParam(required = false) Instant since) {
    return notificationService.getActivity(venueId, since);
  }
}
