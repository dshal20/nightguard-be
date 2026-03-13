package com.nightguard.api.offender;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offenders")
public class OffenderController {

  private final OffenderService offenderService;

  public OffenderController(OffenderService offenderService) {
    this.offenderService = offenderService;
  }

  @PostMapping
  public ResponseEntity<OffenderResponse> createOffender(
      @RequestBody CreateOffenderRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(offenderService.create(request, authentication.getName()));
  }

  @GetMapping
  public ResponseEntity<List<OffenderResponse>> getOffenders(
      @RequestParam UUID venueId,
      Authentication authentication) {
    return ResponseEntity.ok(offenderService.getByVenue(venueId, authentication.getName()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OffenderResponse> getOffender(
      @PathVariable UUID id,
      Authentication authentication) {
    return ResponseEntity.ok(offenderService.getById(id, authentication.getName()));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<OffenderResponse> updateOffender(
      @PathVariable UUID id,
      @RequestBody UpdateOffenderRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(offenderService.update(id, request, authentication.getName()));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOffender(
      @PathVariable UUID id,
      Authentication authentication) {
    offenderService.delete(id, authentication.getName());
  }
}
