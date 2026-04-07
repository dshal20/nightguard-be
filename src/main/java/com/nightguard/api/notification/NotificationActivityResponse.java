package com.nightguard.api.notification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nightguard.api.incident.Incident;
import com.nightguard.api.incident.IncidentSeverity;
import com.nightguard.api.incident.IncidentStatus;
import com.nightguard.api.incident.IncidentType;
import com.nightguard.api.offender.Offender;
import com.nightguard.api.venue.Venue;

public class NotificationActivityResponse {

  private UUID id;
  private NotificationType type;
  private UUID fromVenueId;
  private String fromVenueName;
  private String createdAt;
  private IncidentDetail incident;
  private OffenderDetail offender;

  public static NotificationActivityResponse from(
      Notification notification, Venue fromVenue, Incident incident, Offender offender) {

    NotificationActivityResponse r = new NotificationActivityResponse();
    r.id = notification.getId();
    r.type = notification.getType();
    r.fromVenueId = fromVenue.getId();
    r.fromVenueName = fromVenue.getName();
    r.createdAt = toIso(notification.getCreatedAt());

    if (offender != null) {
      r.offender = new OffenderDetail(
          offender.getId(),
          offender.getFirstName(),
          offender.getLastName(),
          offender.getPhysicalMarkers()
      );
    }

    if (incident != null) {
      List<String> offenderIds = incident.getOffenderIds() == null
          ? List.of()
          : incident.getOffenderIds().stream().map(UUID::toString).collect(Collectors.toList());

      r.incident = new IncidentDetail(
          incident.getId(),
          incident.getType(),
          incident.getSeverity(),
          incident.getStatus(),
          incident.getDescription(),
          incident.getKeywords() != null ? incident.getKeywords() : List.of(),
          offenderIds,
          toIso(incident.getCreatedAt()),
          toIso(incident.getUpdatedAt())
      );
    }
    return r;
  }

  private static String toIso(Instant instant) {
    return instant != null ? instant.toString() : null;
  }

  public UUID getId() { return id; }
  public NotificationType getType() { return type; }
  public UUID getFromVenueId() { return fromVenueId; }
  public String getFromVenueName() { return fromVenueName; }
  public String getCreatedAt() { return createdAt; }
  public IncidentDetail getIncident() { return incident; }
  public OffenderDetail getOffender() { return offender; }

  public record OffenderDetail(
      UUID id,
      String firstName,
      String lastName,
      String physicalMarkers
  ) {}

  public record IncidentDetail(
      UUID id,
      IncidentType type,
      IncidentSeverity severity,
      IncidentStatus status,
      String description,
      List<String> keywords,
      List<String> offenderIds,
      String createdAt,
      String updatedAt
  ) {}
}
