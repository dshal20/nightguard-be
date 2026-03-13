package com.nightguard.api.venue;

import java.time.Instant;
import java.util.UUID;

public class VenueCapacityResponse {

  private UUID id;
  private UUID venueId;
  private String updatedBy;
  private Integer capacity;
  private Instant createdAt;
  private Instant updatedAt;

  public static VenueCapacityResponse from(VenueCapacity capacity) {
    VenueCapacityResponse dto = new VenueCapacityResponse();
    dto.setId(capacity.getId());
    dto.setVenueId(capacity.getVenueId());
    dto.setUpdatedBy(capacity.getUpdatedBy());
    dto.setCapacity(capacity.getCapacity());
    dto.setCreatedAt(capacity.getCreatedAt());
    dto.setUpdatedAt(capacity.getUpdatedAt());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getUpdatedBy() { return updatedBy; }
  public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

  public Integer getCapacity() { return capacity; }
  public void setCapacity(Integer capacity) { this.capacity = capacity; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
