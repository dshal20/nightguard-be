package com.nightguard.api.venue;

import java.util.UUID;

public class NearbyVenueResponse {

  private UUID id;
  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;

  public static NearbyVenueResponse from(Venue venue) {
    NearbyVenueResponse dto = new NearbyVenueResponse();
    dto.setId(venue.getId());
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
