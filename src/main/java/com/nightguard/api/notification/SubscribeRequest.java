package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

public class SubscribeRequest {

  private List<UUID> venueIds;

  public List<UUID> getVenueIds() { return venueIds; }
  public void setVenueIds(List<UUID> venueIds) { this.venueIds = venueIds; }
}
