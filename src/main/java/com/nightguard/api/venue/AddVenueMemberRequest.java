package com.nightguard.api.venue;

public class AddVenueMemberRequest {
  private String userId;
  private VenueRole role;

  public String getUserId() { return userId; }
  public void setUserId(String userId) { this.userId = userId; }

  public VenueRole getRole() { return role; }
  public void setRole(VenueRole role) { this.role = role; }
}
