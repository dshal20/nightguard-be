package com.nightguard.api.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.nightguard.api.fcm.FcmTokenRepository;
import com.nightguard.api.incident.Incident;
import com.nightguard.api.incident.IncidentRepository;
import com.nightguard.api.venue.Venue;
import com.nightguard.api.venue.VenueRepository;

@Service
public class PushNotificationService {

  private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);
  private static final int FCM_BATCH_LIMIT = 500;

  private final NotificationRepository notificationRepository;
  private final NotificationSubscriptionRepository subscriptionRepository;
  private final FcmTokenRepository fcmTokenRepository;
  private final VenueRepository venueRepository;
  private final IncidentRepository incidentRepository;
  private final FirebaseMessaging firebaseMessaging;

  public PushNotificationService(
      NotificationRepository notificationRepository,
      NotificationSubscriptionRepository subscriptionRepository,
      FcmTokenRepository fcmTokenRepository,
      VenueRepository venueRepository,
      IncidentRepository incidentRepository,
      FirebaseMessaging firebaseMessaging) {
    this.notificationRepository = notificationRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.fcmTokenRepository = fcmTokenRepository;
    this.venueRepository = venueRepository;
    this.incidentRepository = incidentRepository;
    this.firebaseMessaging = firebaseMessaging;
  }

  @Async("notificationExecutor")
  public void sendForIncident(UUID venueId, UUID incidentId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.INCIDENT_REPORTED);
    notification.setIncidentId(incidentId);
    notificationRepository.save(notification);

    Incident incident = incidentRepository.findById(incidentId).orElse(null);
    if (incident == null) return;

    String venueName = venueRepository.findById(venueId)
        .map(Venue::getName)
        .orElse("A venue");

    Set<String> tokens = collectTokensForSubscribers(venueId);
    if (tokens.isEmpty()) return;

    String incidentType = formatIncidentType(incident.getType().name());
    String descriptionPreview = truncate(incident.getDescription(), 80);

    com.google.firebase.messaging.Notification fcmNotification =
        com.google.firebase.messaging.Notification.builder()
            .setTitle(incidentType + " @ " + venueName)
            .setBody(descriptionPreview)
            .build();

    Map<String, String> data = Map.of(
        "type", "INCIDENT_REPORTED",
        "incidentId", incidentId.toString(),
        "incidentType", incident.getType().name(),
        "severity", incident.getSeverity().name(),
        "venueName", venueName,
        "venueId", venueId.toString()
    );

    sendInBatches(new ArrayList<>(tokens), fcmNotification, data);
  }

  @Async("notificationExecutor")
  public void sendForOffender(UUID venueId, UUID offenderId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.OFFENDER_ADDED);
    notification.setOffenderId(offenderId);
    notificationRepository.save(notification);

    String venueName = venueRepository.findById(venueId)
        .map(Venue::getName)
        .orElse("A venue");

    Set<String> tokens = collectTokensForSubscribers(venueId);
    if (tokens.isEmpty()) return;

    com.google.firebase.messaging.Notification fcmNotification =
        com.google.firebase.messaging.Notification.builder()
            .setTitle("New Offender Added")
            .setBody(venueName + " has added a new offender to the network")
            .build();

    Map<String, String> data = Map.of(
        "type", "OFFENDER_ADDED",
        "offenderId", offenderId.toString(),
        "venueName", venueName,
        "venueId", venueId.toString()
    );

    sendInBatches(new ArrayList<>(tokens), fcmNotification, data);
  }

  private Set<String> collectTokensForSubscribers(UUID sourceVenueId) {
    List<NotificationSubscription> subscriptions = subscriptionRepository.findByVenueId(sourceVenueId);
    Set<String> tokens = new HashSet<>();
    for (NotificationSubscription sub : subscriptions) {
      tokens.addAll(fcmTokenRepository.findTokensByVenueId(sub.getSubscriber()));
    }
    return tokens;
  }

  private void sendInBatches(
      List<String> tokens,
      com.google.firebase.messaging.Notification notification,
      Map<String, String> data) {

    for (int i = 0; i < tokens.size(); i += FCM_BATCH_LIMIT) {
      List<String> batch = tokens.subList(i, Math.min(i + FCM_BATCH_LIMIT, tokens.size()));
      MulticastMessage message = MulticastMessage.builder()
          .setNotification(notification)
          .putAllData(data)
          .addAllTokens(batch)
          .build();
      try {
        BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
        log.info("FCM batch sent: {}/{} succeeded", response.getSuccessCount(), batch.size());
        cleanupInvalidTokens(batch, response);
      } catch (FirebaseMessagingException e) {
        log.error("FCM batch send failed", e);
      }
    }
  }

  private void cleanupInvalidTokens(List<String> tokens, BatchResponse response) {
    List<SendResponse> responses = response.getResponses();
    for (int i = 0; i < responses.size(); i++) {
      SendResponse sendResponse = responses.get(i);
      if (!sendResponse.isSuccessful()) {
        MessagingErrorCode errorCode = sendResponse.getException().getMessagingErrorCode();
        if (errorCode == MessagingErrorCode.UNREGISTERED
            || errorCode == MessagingErrorCode.INVALID_ARGUMENT) {
          fcmTokenRepository.deleteByToken(tokens.get(i));
          log.debug("Cleared stale FCM token at index {}", i);
        }
      }
    }
  }

  /** "VERBAL_HARASSMENT" → "Verbal Harassment" */
  private String formatIncidentType(String enumName) {
    String[] words = enumName.split("_");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      if (!sb.isEmpty()) sb.append(" ");
      sb.append(Character.toUpperCase(word.charAt(0)));
      sb.append(word.substring(1).toLowerCase());
    }
    return sb.toString();
  }

  private String truncate(String text, int maxLength) {
    if (text == null) return "";
    return text.length() <= maxLength ? text : text.substring(0, maxLength - 1) + "…";
  }
}
