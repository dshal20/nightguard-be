package com.nightguard.api.dto;

import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;

public class UserResponse {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String profileUrl;
  private Role role;

  public static UserResponse fromUser(User user) {
    UserResponse dto = new UserResponse();
    dto.setId(user.getId());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setEmail(user.getEmail());
    dto.setPhoneNumber(user.getPhoneNumber());
    dto.setProfileUrl(user.getProfileUrl());
    dto.setRole(user.getRole());
    return dto;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

  public String getProfileUrl() { return profileUrl; }
  public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }

  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
}
