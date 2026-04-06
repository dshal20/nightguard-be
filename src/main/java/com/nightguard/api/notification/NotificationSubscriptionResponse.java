package com.nightguard.api.notification;

import java.util.UUID;

import com.nightguard.api.venue.Venue;

public class NotificationSubscriptionResponse {

  private UUID id;
  private UUID subscriber;
  private UUID venueId;
  private String venueName;
  private String venueStreetAddress;
  private String venueCity;
  private String venueState;
  private String venuePostalCode;
  private String venuePhoneNumber;

  public static NotificationSubscriptionResponse from(NotificationSubscription subscription, Venue venue) {
    NotificationSubscriptionResponse dto = new NotificationSubscriptionResponse();
    dto.setId(subscription.getId());
    dto.setSubscriber(subscription.getSubscriber());
    dto.setVenueId(subscription.getVenueId());
    dto.setVenueName(venue.getName());
    dto.setVenueStreetAddress(venue.getStreetAddress());
    dto.setVenueCity(venue.getCity());
    dto.setVenueState(venue.getState());
    dto.setVenuePostalCode(venue.getPostalCode());
    dto.setVenuePhoneNumber(venue.getPhoneNumber());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getSubscriber() { return subscriber; }
  public void setSubscriber(UUID subscriber) { this.subscriber = subscriber; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getVenueName() { return venueName; }
  public void setVenueName(String venueName) { this.venueName = venueName; }

  public String getVenueStreetAddress() { return venueStreetAddress; }
  public void setVenueStreetAddress(String venueStreetAddress) { this.venueStreetAddress = venueStreetAddress; }

  public String getVenueCity() { return venueCity; }
  public void setVenueCity(String venueCity) { this.venueCity = venueCity; }

  public String getVenueState() { return venueState; }
  public void setVenueState(String venueState) { this.venueState = venueState; }

  public String getVenuePostalCode() { return venuePostalCode; }
  public void setVenuePostalCode(String venuePostalCode) { this.venuePostalCode = venuePostalCode; }

  public String getVenuePhoneNumber() { return venuePhoneNumber; }
  public void setVenuePhoneNumber(String venuePhoneNumber) { this.venuePhoneNumber = venuePhoneNumber; }
}
