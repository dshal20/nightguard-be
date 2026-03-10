package com.nightguard.api.incident;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

  private final IncidentService incidentService;

  public IncidentController(IncidentService incidentService) {
    this.incidentService = incidentService;
  }

  @PostMapping
  public ResponseEntity<IncidentResponse> createIncident(
      @RequestBody CreateIncidentRequest request,
      Authentication authentication) {
    Incident incident = incidentService.create(request, authentication.getName());
    return ResponseEntity.ok(IncidentResponse.from(incident));
  }

  @GetMapping
  public ResponseEntity<List<IncidentResponse>> getIncidents(
      @RequestParam UUID venueId,
      Authentication authentication) {
    List<IncidentResponse> incidents = incidentService.getByVenue(venueId, authentication.getName()).stream()
        .map(IncidentResponse::from)
        .toList();
    return ResponseEntity.ok(incidents);
  }

  @GetMapping("/{id}")
  public ResponseEntity<IncidentResponse> getIncident(
      @PathVariable UUID id,
      Authentication authentication) {
    Incident incident = incidentService.getById(id, authentication.getName());
    return ResponseEntity.ok(IncidentResponse.from(incident));
  }
}
