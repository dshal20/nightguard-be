package com.nightguard.api.notification;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final NotificationSubscriptionRepository subscriptionRepository;

  public NotificationService(NotificationSubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public List<NotificationSubscription> subscribe(SubscribeRequest request, String userId) {
    List<NotificationSubscription> subscriptions = request.getVenueIds().stream()
        .map(venueId -> {
          NotificationSubscription subscription = new NotificationSubscription();
          subscription.setSubscriber(userId);
          subscription.setVenueId(venueId);
          return subscription;
        })
        .toList();

    return subscriptionRepository.saveAll(subscriptions);
  }
}
