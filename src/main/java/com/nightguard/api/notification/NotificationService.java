package com.nightguard.api.notification;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;

@Service
public class NotificationService {

  private final NotificationSubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;

  public NotificationService(NotificationSubscriptionRepository subscriptionRepository,
      UserRepository userRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
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

  public void registerDevice(String userId, String fcmToken) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND));
    user.setFcmToken(fcmToken);
    userRepository.save(user);
  }
}
