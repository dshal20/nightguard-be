package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.nightguard.api.user.UserRepository;
import com.nightguard.api.venue.VenueMemberRepository;

@Service
public class PushNotificationService {

  private final FirebaseMessaging firebaseMessaging;
  private final NotificationRepository notificationRepository;
  private final NotificationSubscriptionRepository subscriptionRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final UserRepository userRepository;

  public PushNotificationService(FirebaseMessaging firebaseMessaging,
      NotificationRepository notificationRepository,
      NotificationSubscriptionRepository subscriptionRepository,
      VenueMemberRepository venueMemberRepository,
      UserRepository userRepository) {
    this.firebaseMessaging = firebaseMessaging;
    this.notificationRepository = notificationRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.venueMemberRepository = venueMemberRepository;
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
    // Find all venues subscribed to this venue, then notify their members
    List<UUID> subscriberVenueIds = subscriptionRepository.findByVenueId(venueId).stream()
        .map(NotificationSubscription::getSubscriber)
        .toList();

    if (subscriberVenueIds.isEmpty()) return;

    List<String> userIds = subscriberVenueIds.stream()
        .flatMap(svId -> venueMemberRepository.findByVenueId(svId).stream())
        .map(member -> member.getUserId())
        .distinct()
        .toList();

    if (userIds.isEmpty()) return;

    List<String> tokens = userRepository.findAllById(userIds).stream()
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
