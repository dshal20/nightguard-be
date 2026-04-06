package com.nightguard.api.notification;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "from_venue", nullable = false)
  private UUID fromVenue;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, columnDefinition = "notification_type")
  private NotificationType type;

  @Column(name = "incident_id")
  private UUID incidentId;

  @Column(name = "offender_id")
  private UUID offenderId;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @PrePersist
  void prePersist() {
    this.createdAt = Instant.now();
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getFromVenue() { return fromVenue; }
  public void setFromVenue(UUID fromVenue) { this.fromVenue = fromVenue; }

  public NotificationType getType() { return type; }
  public void setType(NotificationType type) { this.type = type; }

  public UUID getIncidentId() { return incidentId; }
  public void setIncidentId(UUID incidentId) { this.incidentId = incidentId; }

  public UUID getOffenderId() { return offenderId; }
  public void setOffenderId(UUID offenderId) { this.offenderId = offenderId; }

  public Instant getCreatedAt() { return createdAt; }
}
