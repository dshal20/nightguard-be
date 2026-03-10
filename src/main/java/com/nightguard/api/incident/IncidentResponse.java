package com.nightguard.api.incident;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class IncidentResponse {
  private UUID id;
  private UUID venueId;
  private String reporterId;
  private IncidentType type;
  private IncidentSeverity severity;
  private String description;
  private List<String> keywords;
  private Instant createdAt;
  private Instant updatedAt;

  public static IncidentResponse from(Incident incident) {
    IncidentResponse res = new IncidentResponse();
    res.id = incident.getId();
    res.venueId = incident.getVenueId();
    res.reporterId = incident.getReporterId();
    res.type = incident.getType();
    res.severity = incident.getSeverity();
    res.description = incident.getDescription();
    res.keywords = incident.getKeywords();
    res.createdAt = incident.getCreatedAt();
    res.updatedAt = incident.getUpdatedAt();
    return res;
  }

  public UUID getId() { return id; }
  public UUID getVenueId() { return venueId; }
  public String getReporterId() { return reporterId; }
  public IncidentType getType() { return type; }
  public IncidentSeverity getSeverity() { return severity; }
  public String getDescription() { return description; }
  public List<String> getKeywords() { return keywords; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}
