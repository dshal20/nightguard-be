package com.nightguard.api.offender;

import java.util.List;
import java.util.UUID;

public class CreateOffenderRequest {

  private UUID venueId;
  private String firstName;
  private String lastName;
  private String physicalMarkers;
  private Integer riskScore;
  private String currentStatus;
  private UUID globalId;
  private String notes;
  private List<String> photoUrls;

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

  public List<String> getPhotoUrls() { return photoUrls; }
  public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }
}
