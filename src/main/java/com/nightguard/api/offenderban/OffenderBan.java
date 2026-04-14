package com.nightguard.api.offenderban;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "offender_bans")
public class OffenderBan {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "offender_id")
  private UUID offenderId;

  @Column(name = "type", columnDefinition = "ban_type")
  private String type;

  @Column(name = "issued_by")
  private String issuedBy;

  @Column(name = "issued_at", updatable = false)
  private Instant issuedAt;

  @Column(name = "expires_at")
  private Instant expiresAt;

  @PrePersist
  void prePersist() {
    this.issuedAt = Instant.now();
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getOffenderId() { return offenderId; }
  public void setOffenderId(UUID offenderId) { this.offenderId = offenderId; }

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public String getIssuedBy() { return issuedBy; }
  public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

  public Instant getIssuedAt() { return issuedAt; }
  public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }

  public Instant getExpiresAt() { return expiresAt; }
  public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
