package com.nightguard.api.notification;

import com.nightguard.api.incident.IncidentSeverity;

public class UpdateNotificationLevelRequest {

  private IncidentSeverity notificationLevel;

  public IncidentSeverity getNotificationLevel() { return notificationLevel; }
  public void setNotificationLevel(IncidentSeverity notificationLevel) { this.notificationLevel = notificationLevel; }
}
