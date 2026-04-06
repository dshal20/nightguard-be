package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.nightguard.api.user.UserRepository;

@Service
public class PushNotificationService {

  private final FirebaseMessaging firebaseMessaging;
  private final NotificationRepository notificationRepository;
  private final NotificationSubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;

  public PushNotificationService(FirebaseMessaging firebaseMessaging,
      NotificationRepository notificationRepository,
      NotificationSubscriptionRepository subscriptionRepository,
      UserRepository userRepository) {
    this.firebaseMessaging = firebaseMessaging;
    this.notificationRepository = notificationRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
  }

  public void sendForIncident(UUID venueId, UUID incidentId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.INCIDENT_REPORTED);
    notification.setIncidentId(incidentId);
    notificationRepository.save(notification);

    dispatch(venueId, "Incident Reported", "A new incident has been reported at a venue you follow.");
  }

  public void sendForOffender(UUID venueId, UUID offenderId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.OFFENDER_ADDED);
    notification.setOffenderId(offenderId);
    notificationRepository.save(notification);

    dispatch(venueId, "Offender Added", "A new offender has been flagged at a venue you follow.");
  }

  private void dispatch(UUID venueId, String title, String body) {
    List<String> subscriberIds = subscriptionRepository.findByVenueId(venueId).stream()
        .map(NotificationSubscription::getSubscriber)
        .toList();

    if (subscriberIds.isEmpty()) return;

    List<String> tokens = userRepository.findAllById(subscriberIds).stream()
        .map(user -> user.getFcmToken())
        .filter(token -> token != null && !token.isBlank())
        .toList();

    if (tokens.isEmpty()) return;

    MulticastMessage message = MulticastMessage.builder()
        .setNotification(com.google.firebase.messaging.Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build())
        .addAllTokens(tokens)
        .build();

    try {
      firebaseMessaging.sendEachForMulticast(message);
    } catch (FirebaseMessagingException e) {
      // log and continue — notification delivery failure should not fail the request
    }
  }
}
