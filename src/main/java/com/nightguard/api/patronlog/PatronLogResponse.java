package com.nightguard.api.patronlog;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.nightguard.api.dto.UserResponse;
import com.nightguard.api.user.User;

public class PatronLogResponse {

  private UUID id;
  private UUID venueId;
  private String firstName;
  private String lastName;
  private String middleName;
  private String driversLicenseId;
  private LocalDate dateOfBirth;
  private LocalDate expirationDate;
  private String state;
  private String streetAddress;
  private String city;
  private String postalCode;
  private String gender;
  private String eyeColor;
  private String decision;
  private UserResponse recordedBy;
  private Instant createdAt;

  public static PatronLogResponse from(PatronLog log, User recordedBy) {
    PatronLogResponse dto = new PatronLogResponse();
    dto.setId(log.getId());
    dto.setVenueId(log.getVenueId());
    dto.setFirstName(log.getFirstName());
    dto.setLastName(log.getLastName());
    dto.setMiddleName(log.getMiddleName());
    dto.setDriversLicenseId(log.getDriversLicenseId());
    dto.setDateOfBirth(log.getDateOfBirth());
    dto.setExpirationDate(log.getExpirationDate());
    dto.setState(log.getState());
    dto.setStreetAddress(log.getStreetAddress());
    dto.setCity(log.getCity());
    dto.setPostalCode(log.getPostalCode());
    dto.setGender(log.getGender());
    dto.setEyeColor(log.getEyeColor());
    dto.setDecision(log.getDecision());
    dto.setRecordedBy(recordedBy != null ? UserResponse.fromUser(recordedBy) : null);
    dto.setCreatedAt(log.getCreatedAt());
    return dto;
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getVenueId() { return venueId; }
  public void setVenueId(UUID venueId) { this.venueId = venueId; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getMiddleName() { return middleName; }
  public void setMiddleName(String middleName) { this.middleName = middleName; }

  public String getDriversLicenseId() { return driversLicenseId; }
  public void setDriversLicenseId(String driversLicenseId) { this.driversLicenseId = driversLicenseId; }

  public LocalDate getDateOfBirth() { return dateOfBirth; }
  public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

  public LocalDate getExpirationDate() { return expirationDate; }
  public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

  public String getState() { return state; }
  public void setState(String state) { this.state = state; }

  public String getStreetAddress() { return streetAddress; }
  public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }

  public String getEyeColor() { return eyeColor; }
  public void setEyeColor(String eyeColor) { this.eyeColor = eyeColor; }

  public String getDecision() { return decision; }
  public void setDecision(String decision) { this.decision = decision; }

  public UserResponse getRecordedBy() { return recordedBy; }
  public void setRecordedBy(UserResponse recordedBy) { this.recordedBy = recordedBy; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
