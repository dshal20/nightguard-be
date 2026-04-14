package com.nightguard.api.patronlog;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "patron_log")
public class PatronLog {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "venue_id", nullable = false)
  private UUID venueId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "drivers_license_id")
  private String driversLicenseId;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "expiration_date")
  private LocalDate expirationDate;

  @Column(name = "state")
  private String state;

  @Column(name = "street_address")
  private String streetAddress;

  @Column(name = "city")
  private String city;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "gender")
  private String gender;

  @Column(name = "eye_color")
  private String eyeColor;

  @Column(name = "decision", nullable = false)
  private String decision;

  @Column(name = "recorded_by", nullable = false)
  private String recordedBy;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @PrePersist
  void prePersist() {
    this.createdAt = Instant.now();
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

  public String getRecordedBy() { return recordedBy; }
  public void setRecordedBy(String recordedBy) { this.recordedBy = recordedBy; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
