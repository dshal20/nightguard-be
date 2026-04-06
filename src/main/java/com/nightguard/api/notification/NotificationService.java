package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NotificationService {

  private final NotificationSubscriptionRepository subscriptionRepository;

  public NotificationService(NotificationSubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public List<NotificationSubscription> subscribe(UUID subscriberVenueId, List<UUID> venueIds) {
    List<NotificationSubscription> subscriptions = venueIds.stream()
        .map(venueId -> {
          NotificationSubscription subscription = new NotificationSubscription();
          subscription.setSubscriber(subscriberVenueId);
          subscription.setVenueId(venueId);
          return subscription;
        })
        .toList();
    return subscriptionRepository.saveAll(subscriptions);
  }

  public List<NotificationSubscription> getSubscriptions(UUID subscriberVenueId) {
    return subscriptionRepository.findBySubscriber(subscriberVenueId);
  }

  public void unsubscribe(UUID subscriberVenueId, UUID venueId) {
    NotificationSubscription subscription = subscriptionRepository
        .findBySubscriberAndVenueId(subscriberVenueId, venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    subscriptionRepository.delete(subscription);
  }
}
