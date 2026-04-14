package com.nightguard.api.offendercomment;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OffenderCommentController {

  private final OffenderCommentService offenderCommentService;

  public OffenderCommentController(OffenderCommentService offenderCommentService) {
    this.offenderCommentService = offenderCommentService;
  }

  @PostMapping("/offenders/{id}/comments")
  public ResponseEntity<OffenderCommentResponse> createComment(
      @PathVariable UUID id,
      @RequestBody CreateOffenderCommentRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(offenderCommentService.create(id, request, authentication.getName()));
  }

  @GetMapping("/offenders/{id}/comments")
  public ResponseEntity<List<OffenderCommentResponse>> getComments(
      @PathVariable UUID id,
      Authentication authentication) {
    return ResponseEntity.ok(offenderCommentService.getByOffender(id, authentication.getName()));
  }

  @DeleteMapping("/offender-comments/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteComment(
      @PathVariable UUID id,
      Authentication authentication) {
    offenderCommentService.delete(id, authentication.getName());
  }
}
