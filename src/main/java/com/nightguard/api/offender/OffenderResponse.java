package com.nightguard.api.offender;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nightguard.api.incident.IncidentResponse;

public class OffenderResponse {

  private UUID id;
  private UUID venueId;
  private String firstName;
  private String lastName;
  private String physicalMarkers;
  private Integer riskScore;
  private String currentStatus;
  private UUID globalId;
  private String notes;
  private Instant createdAt;
  private Instant updatedAt;
  private List<IncidentResponse> incidents;

  public static OffenderResponse from(Offender offender) {
    return from(offender, null);
  }

  public static OffenderResponse from(Offender offender, List<IncidentResponse> incidents) {
    OffenderResponse dto = new OffenderResponse();
    dto.setId(offender.getId());
    dto.setVenueId(offender.getVenueId());
    dto.setFirstName(offender.getFirstName());
    dto.setLastName(offender.getLastName());
    dto.setPhysicalMarkers(offender.getPhysicalMarkers());
    dto.setRiskScore(offender.getRiskScore());
    dto.setCurrentStatus(offender.getCurrentStatus());
    dto.setGlobalId(offender.getGlobalId());
    dto.setNotes(offender.getNotes());
    dto.setCreatedAt(offender.getCreatedAt());
    dto.setUpdatedAt(offender.getUpdatedAt());
    dto.setIncidents(incidents);
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getPhysicalMarkers() { return physicalMarkers; }
  public void setPhysicalMarkers(String physicalMarkers) { this.physicalMarkers = physicalMarkers; }

  public Integer getRiskScore() { return riskScore; }
  public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }

  public String getCurrentStatus() { return currentStatus; }
  public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

  public UUID getGlobalId() { return globalId; }
  public void setGlobalId(UUID globalId) { this.globalId = globalId; }

  public String getNotes() { return notes; }
  public void setNotes(String notes) { this.notes = notes; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

  public List<IncidentResponse> getIncidents() { return incidents; }
  public void setIncidents(List<IncidentResponse> incidents) { this.incidents = incidents; }
}
