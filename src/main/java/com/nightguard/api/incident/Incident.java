package com.nightguard.api.incident;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "incidents")
public class Incident {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "venue_id", nullable = false)
  private UUID venueId;

  @Column(name = "reporter_id", nullable = false)
  private String reporterId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private IncidentType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "severity")
  private IncidentSeverity severity;

  @Column(name = "description")
  private String description;

  @JdbcTypeCode(SqlTypes.ARRAY)
  @Column(name = "keywords", columnDefinition = "TEXT[]")
  private List<String> keywords;

  @JdbcTypeCode(SqlTypes.ARRAY)
  @Column(name = "offender_ids", columnDefinition = "UUID[]")
  private List<UUID> offenderIds = new ArrayList<>();

  @JdbcTypeCode(SqlTypes.ARRAY)
  @Column(name = "media_urls", columnDefinition = "TEXT[]")
  private List<String> mediaUrls = new ArrayList<>();

  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "incident_status")
  private IncidentStatus status;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
    if (this.status == null) this.status = IncidentStatus.ACTIVE;
  }

  @PreUpdate
  void preUpdate() {
    this.updatedAt = Instant.now();
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getReporterId() { return reporterId; }
  public void setReporterId(String reporterId) { this.reporterId = reporterId; }

  public IncidentType getType() { return type; }
  public void setType(IncidentType type) { this.type = type; }

  public IncidentSeverity getSeverity() { return severity; }
  public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public List<String> getKeywords() { return keywords; }
  public void setKeywords(List<String> keywords) { this.keywords = keywords; }

  public List<UUID> getOffenderIds() { return offenderIds; }
  public void setOffenderIds(List<UUID> offenderIds) { this.offenderIds = offenderIds; }

  public List<String> getMediaUrls() { return mediaUrls; }
  public void setMediaUrls(List<String> mediaUrls) { this.mediaUrls = mediaUrls; }

  public IncidentStatus getStatus() { return status; }
  public void setStatus(IncidentStatus status) { this.status = status; }

  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}
