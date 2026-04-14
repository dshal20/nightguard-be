package com.nightguard.api.venue;

public class UpdateVenueRequest {

  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
  private String venueImageUrl;

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

  public String getVenueImageUrl() { return venueImageUrl; }
  public void setVenueImageUrl(String venueImageUrl) { this.venueImageUrl = venueImageUrl; }
}
