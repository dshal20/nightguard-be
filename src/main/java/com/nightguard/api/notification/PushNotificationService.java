package com.nightguard.api.notification;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

  private final NotificationRepository notificationRepository;

  public PushNotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  public void sendForIncident(UUID venueId, UUID incidentId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.INCIDENT_REPORTED);
    notification.setIncidentId(incidentId);
    notificationRepository.save(notification);
  }

  public void sendForOffender(UUID venueId, UUID offenderId) {
    Notification notification = new Notification();
    notification.setFromVenue(venueId);
    notification.setType(NotificationType.OFFENDER_ADDED);
    notification.setOffenderId(offenderId);
    notificationRepository.save(notification);
  }
}
