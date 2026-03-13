package com.nightguard.api.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateIncidentRequest {

  private UUID venueId;
  private IncidentType type;
  private IncidentSeverity severity;
  private String description;
  private List<String> keywords;
  private IncidentStatus status;
  private List<UUID> offenderIds = new ArrayList<>();

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public IncidentType getType() { return type; }
  public void setType(IncidentType type) { this.type = type; }

  public IncidentSeverity getSeverity() { return severity; }
  public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public List<String> getKeywords() { return keywords; }
  public void setKeywords(List<String> keywords) { this.keywords = keywords; }

  public IncidentStatus getStatus() { return status; }
  public void setStatus(IncidentStatus status) { this.status = status; }

  public List<UUID> getOffenderIds() { return offenderIds; }
  public void setOffenderIds(List<UUID> offenderIds) { this.offenderIds = offenderIds; }
}
