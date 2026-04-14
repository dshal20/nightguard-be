package com.nightguard.api.offenderban;

import java.time.Instant;

public class IssueBanRequest {

  private String type;
  private Instant expiresAt;

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public Instant getExpiresAt() { return expiresAt; }
  public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
