package com.nightguard.api.incident;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nightguard.api.dto.UserResponse;
import com.nightguard.api.user.User;

public class IncidentResponse {

  private UUID id;
  private UUID venueId;
  private UserResponse reporter;
  private IncidentType type;
  private IncidentSeverity severity;
  private String description;
  private List<String> keywords;
  private List<UUID> offenderIds;
  private List<String> mediaUrls;
  private IncidentStatus status;
  private Instant createdAt;
  private Instant updatedAt;

  public static IncidentResponse from(Incident incident, User reporter) {
    IncidentResponse res = new IncidentResponse();
    res.id = incident.getId();
    res.venueId = incident.getVenueId();
    res.reporter = UserResponse.fromUser(reporter);
    res.type = incident.getType();
    res.severity = incident.getSeverity();
    res.description = incident.getDescription();
    res.keywords = incident.getKeywords();
    res.offenderIds = incident.getOffenderIds();
    res.mediaUrls = incident.getMediaUrls();
    res.status = incident.getStatus();
    res.createdAt = incident.getCreatedAt();
    res.updatedAt = incident.getUpdatedAt();
    return res;
  }

  public UUID getId() { return id; }
  public UUID getVenueId() { return venueId; }
  public UserResponse getReporter() { return reporter; }
  public IncidentType getType() { return type; }
  public IncidentSeverity getSeverity() { return severity; }
  public String getDescription() { return description; }
  public List<String> getKeywords() { return keywords; }
  public List<UUID> getOffenderIds() { return offenderIds; }
  public List<String> getMediaUrls() { return mediaUrls; }
  public IncidentStatus getStatus() { return status; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}
