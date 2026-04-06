package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.venue.Venue;
import com.nightguard.api.venue.VenueRepository;

@Service
public class NotificationService {

  private final NotificationSubscriptionRepository subscriptionRepository;
  private final VenueRepository venueRepository;

  public NotificationService(NotificationSubscriptionRepository subscriptionRepository,
      VenueRepository venueRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.venueRepository = venueRepository;
  }

  public List<NotificationSubscriptionResponse> subscribe(UUID subscriberVenueId, List<UUID> venueIds) {
    List<NotificationSubscription> subscriptions = venueIds.stream()
        .map(venueId -> {
          NotificationSubscription subscription = new NotificationSubscription();
          subscription.setSubscriber(subscriberVenueId);
          subscription.setVenueId(venueId);
          return subscription;
        })
        .toList();
    return toResponse(subscriptionRepository.saveAll(subscriptions));
  }

  public List<NotificationSubscriptionResponse> getSubscriptions(UUID subscriberVenueId) {
    return toResponse(subscriptionRepository.findBySubscriber(subscriberVenueId));
  }

  public void unsubscribe(UUID subscriberVenueId, UUID venueId) {
    NotificationSubscription subscription = subscriptionRepository
        .findBySubscriberAndVenueId(subscriberVenueId, venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    subscriptionRepository.delete(subscription);
  }

  private List<NotificationSubscriptionResponse> toResponse(List<NotificationSubscription> subscriptions) {
    return subscriptions.stream().map(sub -> {
      Venue venue = venueRepository.findById(sub.getVenueId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      return NotificationSubscriptionResponse.from(sub, venue);
    }).toList();
  }
}
