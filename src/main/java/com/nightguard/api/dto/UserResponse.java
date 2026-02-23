package com.nightguard.api.dto;

import com.google.firebase.auth.FirebaseToken;

public class UserResponse {
  private String uid;
  private String email;
  private String name;

  public static UserResponse fromToken(FirebaseToken token) {
    UserResponse dto = new UserResponse();
    dto.setUid(token.getUid());
    dto.setEmail(token.getEmail());
    dto.setName(token.getName());
    return dto;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
