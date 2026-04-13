package com.nightguard.api.notification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

import com.nightguard.api.incident.Incident;
import com.nightguard.api.incident.IncidentRepository;
import com.nightguard.api.incident.IncidentSeverity;
import com.nightguard.api.offender.Offender;
import com.nightguard.api.offender.OffenderRepository;
import com.nightguard.api.venue.Venue;
import com.nightguard.api.venue.VenueRepository;

@Service
public class NotificationService {

  private final NotificationSubscriptionRepository subscriptionRepository;
  private final NotificationRepository notificationRepository;
  private final VenueRepository venueRepository;
  private final IncidentRepository incidentRepository;
  private final OffenderRepository offenderRepository;

  public NotificationService(
      NotificationSubscriptionRepository subscriptionRepository,
      NotificationRepository notificationRepository,
      VenueRepository venueRepository,
      IncidentRepository incidentRepository,
      OffenderRepository offenderRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.notificationRepository = notificationRepository;
    this.venueRepository = venueRepository;
    this.incidentRepository = incidentRepository;
    this.offenderRepository = offenderRepository;
  }

  public List<NotificationSubscriptionResponse> subscribe(UUID subscriberVenueId, List<UUID> venueIds, IncidentSeverity notificationLevel) {
    List<NotificationSubscription> subscriptions = venueIds.stream()
        .map(venueId -> {
          NotificationSubscription subscription = new NotificationSubscription();
          subscription.setSubscriber(subscriberVenueId);
          subscription.setVenueId(venueId);
          subscription.setNotificationLevel(notificationLevel != null ? notificationLevel : IncidentSeverity.LOW);
          return subscription;
        })
        .toList();
    return toResponse(subscriptionRepository.saveAll(subscriptions));
  }

  public NotificationSubscriptionResponse updateNotificationLevel(UUID subscriberVenueId, UUID venueId, IncidentSeverity notificationLevel) {
    NotificationSubscription subscription = subscriptionRepository
        .findBySubscriberAndVenueId(subscriberVenueId, venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    subscription.setNotificationLevel(notificationLevel);
    return toResponse(List.of(subscriptionRepository.save(subscription))).get(0);
  }

  public List<NotificationSubscriptionResponse> getSubscriptions(UUID subscriberVenueId) {
    return toResponse(subscriptionRepository.findBySubscriberOrderByCreatedAtAscIdAsc(subscriberVenueId));
  }

  public void unsubscribe(UUID subscriberVenueId, UUID venueId) {
    NotificationSubscription subscription = subscriptionRepository
        .findBySubscriberAndVenueId(subscriberVenueId, venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    subscriptionRepository.delete(subscription);
  }

  /**
   * Returns notifications from all venues that subscriberVenueId is subscribed to,
   * enriched with incident details, sorted newest first.
   */
  public List<NotificationActivityResponse> getActivity(UUID subscriberVenueId, Instant since) {
    List<NotificationSubscription> subscriptions = subscriptionRepository.findBySubscriberOrderByCreatedAtAscIdAsc(subscriberVenueId);

    if (subscriptions.isEmpty()) return List.of();

    Map<UUID, IncidentSeverity> minLevelByVenue = subscriptions.stream()
        .collect(Collectors.toMap(NotificationSubscription::getVenueId, NotificationSubscription::getNotificationLevel));

    List<UUID> watchedVenueIds = subscriptions.stream()
        .map(NotificationSubscription::getVenueId)
        .toList();

    List<Notification> notifications = since != null
        ? notificationRepository.findByFromVenueInAndCreatedAtGreaterThanEqualOrderByCreatedAtDesc(watchedVenueIds, since)
        : notificationRepository.findByFromVenueInOrderByCreatedAtDesc(watchedVenueIds);

    return notifications
        .stream()
        .map(n -> {
          Venue fromVenue = venueRepository.findById(n.getFromVenue())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
          Incident incident = n.getIncidentId() != null
              ? incidentRepository.findById(n.getIncidentId()).orElse(null)
              : null;
          Offender offender = n.getOffenderId() != null
              ? offenderRepository.findById(n.getOffenderId()).orElse(null)
              : null;
          return NotificationActivityResponse.from(n, fromVenue, incident, offender);
        })
        .filter(n -> {
          if (n.getIncident() == null) return true;
          IncidentSeverity minLevel = minLevelByVenue.getOrDefault(n.getFromVenueId(), IncidentSeverity.LOW);
          return n.getIncident().severity().ordinal() >= minLevel.ordinal();
        })
        .toList();
  }

  private List<NotificationSubscriptionResponse> toResponse(List<NotificationSubscription> subscriptions) {
    return subscriptions.stream().map(sub -> {
      Venue venue = venueRepository.findById(sub.getVenueId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      return NotificationSubscriptionResponse.from(sub, venue);
    }).toList();
  }
}
