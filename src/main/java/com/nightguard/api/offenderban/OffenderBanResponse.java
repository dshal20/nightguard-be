package com.nightguard.api.offenderban;

import java.time.Instant;
import java.util.UUID;

import com.nightguard.api.dto.UserResponse;
import com.nightguard.api.user.User;

public class OffenderBanResponse {

  private UUID id;
  private UUID offenderId;
  private String type;
  private UserResponse issuedBy;
  private Instant issuedAt;
  private Instant expiresAt;

  public static OffenderBanResponse from(OffenderBan ban, User issuedBy) {
    OffenderBanResponse res = new OffenderBanResponse();
    res.id = ban.getId();
    res.offenderId = ban.getOffenderId();
    res.type = ban.getType();
    res.issuedBy = UserResponse.fromUser(issuedBy);
    res.issuedAt = ban.getIssuedAt();
    res.expiresAt = ban.getExpiresAt();
    return res;
  }

  public UUID getId() { return id; }
  public UUID getOffenderId() { return offenderId; }
  public String getType() { return type; }
  public UserResponse getIssuedBy() { return issuedBy; }
  public Instant getIssuedAt() { return issuedAt; }
  public Instant getExpiresAt() { return expiresAt; }
}
