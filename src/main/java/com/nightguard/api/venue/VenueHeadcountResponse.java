package com.nightguard.api.venue;

import java.time.Instant;
import java.util.UUID;

public class VenueHeadcountResponse {

  private UUID id;
  private UUID venueId;
  private Integer headcount;
  private String recordedBy;
  private Instant createdAt;

  public static VenueHeadcountResponse from(VenueHeadcount headcount) {
    VenueHeadcountResponse dto = new VenueHeadcountResponse();
    dto.setId(headcount.getId());
    dto.setVenueId(headcount.getVenueId());
    dto.setHeadcount(headcount.getHeadcount());
    dto.setRecordedBy(headcount.getRecordedBy());
    dto.setCreatedAt(headcount.getCreatedAt());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public Integer getHeadcount() { return headcount; }
  public void setHeadcount(Integer headcount) { this.headcount = headcount; }

  public String getRecordedBy() { return recordedBy; }
  public void setRecordedBy(String recordedBy) { this.recordedBy = recordedBy; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
