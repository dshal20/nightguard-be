package com.nightguard.api.offenderban;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OffenderBanController {

  private final OffenderBanService offenderBanService;

  public OffenderBanController(OffenderBanService offenderBanService) {
    this.offenderBanService = offenderBanService;
  }

  @PostMapping("/offenders/{id}/bans")
  public ResponseEntity<OffenderBanResponse> issueBan(
      @PathVariable UUID id,
      @RequestBody IssueBanRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(offenderBanService.issue(id, request, authentication.getName()));
  }

  @GetMapping("/offenders/{id}/bans")
  public ResponseEntity<List<OffenderBanResponse>> getBans(
      @PathVariable UUID id,
      Authentication authentication) {
    return ResponseEntity.ok(offenderBanService.getByOffender(id, authentication.getName()));
  }
}
