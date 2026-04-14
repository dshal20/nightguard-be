package com.nightguard.api.offendercomment;

import java.time.Instant;
import java.util.UUID;

import com.nightguard.api.dto.UserResponse;
import com.nightguard.api.user.User;

public class OffenderCommentResponse {

  private UUID id;
  private UUID offenderId;
  private UserResponse author;
  private String comment;
  private Instant createdAt;

  public static OffenderCommentResponse from(OffenderComment offenderComment, User author) {
    OffenderCommentResponse res = new OffenderCommentResponse();
    res.id = offenderComment.getId();
    res.offenderId = offenderComment.getOffenderId();
    res.author = UserResponse.fromUser(author);
    res.comment = offenderComment.getComment();
    res.createdAt = offenderComment.getCreatedAt();
    return res;
  }

  public UUID getId() { return id; }
  public UUID getOffenderId() { return offenderId; }
  public UserResponse getAuthor() { return author; }
  public String getComment() { return comment; }
  public Instant getCreatedAt() { return createdAt; }
}
