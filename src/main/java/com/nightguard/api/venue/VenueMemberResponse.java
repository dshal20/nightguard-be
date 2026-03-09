package com.nightguard.api.venue;

import java.time.Instant;
import java.util.UUID;

public class VenueMemberResponse {

  private UUID id;
  private UUID venueId;
  private String userId;
  private VenueRole role;
  private Instant createdAt;
  private Instant updatedAt;

  public static VenueMemberResponse from(VenueMember member) {
    VenueMemberResponse dto = new VenueMemberResponse();
    dto.setId(member.getId());
    dto.setVenueId(member.getVenueId());
    dto.setUserId(member.getUserId());
    dto.setRole(member.getRole());
    dto.setCreatedAt(member.getCreatedAt());
    dto.setUpdatedAt(member.getUpdatedAt());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getUserId() { return userId; }
  public void setUserId(String userId) { this.userId = userId; }

  public VenueRole getRole() { return role; }
  public void setRole(VenueRole role) { this.role = role; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


