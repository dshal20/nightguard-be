package com.nightguard.api.notification;

import java.util.UUID;

import com.nightguard.api.incident.IncidentSeverity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_subscriptions")
public class NotificationSubscription {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "subscriber", nullable = false)
  private UUID subscriber;

  @Column(name = "venue_id", nullable = false)
  private UUID venueId;

  @Enumerated(EnumType.STRING)
  @Column(name = "notification_level", nullable = false)
  private IncidentSeverity notificationLevel = IncidentSeverity.LOW;

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getSubscriber() { return subscriber; }
  public void setSubscriber(UUID subscriber) { this.subscriber = subscriber; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public IncidentSeverity getNotificationLevel() { return notificationLevel; }
  public void setNotificationLevel(IncidentSeverity notificationLevel) { this.notificationLevel = notificationLevel; }
}
