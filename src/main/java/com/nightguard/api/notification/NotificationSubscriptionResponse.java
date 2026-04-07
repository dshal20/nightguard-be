package com.nightguard.api.notification;

import java.util.UUID;

import com.nightguard.api.venue.Venue;

public class NotificationSubscriptionResponse {

  private UUID id;
  private UUID subscriber;
  private UUID venueId;
  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;

  public static NotificationSubscriptionResponse from(NotificationSubscription subscription, Venue venue) {
    NotificationSubscriptionResponse dto = new NotificationSubscriptionResponse();
    dto.setId(subscription.getId());
    dto.setSubscriber(subscription.getSubscriber());
    dto.setVenueId(subscription.getVenueId());
    dto.setName(venue.getName());
    dto.setStreetAddress(venue.getStreetAddress());
    dto.setCity(venue.getCity());
    dto.setState(venue.getState());
    dto.setPostalCode(venue.getPostalCode());
    dto.setPhoneNumber(venue.getPhoneNumber());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getSubscriber() { return subscriber; }
  public void setSubscriber(UUID subscriber) { this.subscriber = subscriber; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getStreetAddress() { return streetAddress; }
  public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public String getState() { return state; }
  public void setState(String state) { this.state = state; }

  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
