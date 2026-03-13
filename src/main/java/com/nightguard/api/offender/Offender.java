package com.nightguard.api.offender;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "offenders")
public class Offender {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "venue_id")
  private UUID venueId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "physical_markers")
  private String physicalMarkers;

  @Column(name = "risk_score")
  private Integer riskScore;

  @Column(name = "current_status")
  private String currentStatus;

  @Column(name = "global_id")
  private UUID globalId;

  @Column(name = "notes")
  private String notes;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  void preUpdate() {
    this.updatedAt = Instant.now();
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
}
