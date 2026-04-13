package com.nightguard.api.notification;

import java.util.List;
import java.util.UUID;

import com.nightguard.api.incident.IncidentSeverity;

public class SubscribeRequest {

  private List<UUID> venueIds;
  private IncidentSeverity notificationLevel = IncidentSeverity.LOW;

  public List<UUID> getVenueIds() { return venueIds; }
  public void setVenueIds(List<UUID> venueIds) { this.venueIds = venueIds; }

  public IncidentSeverity getNotificationLevel() { return notificationLevel; }
  public void setNotificationLevel(IncidentSeverity notificationLevel) { this.notificationLevel = notificationLevel; }
}
