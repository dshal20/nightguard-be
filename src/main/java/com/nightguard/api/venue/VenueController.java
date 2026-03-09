package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/venues")
public class VenueController {

  private final VenueRepository venueRepository;

  private final VenueService venueService;

  public VenueController(VenueService venueService, VenueRepository venueRepository) {
    this.venueService = venueService;
    this.venueRepository = venueRepository;
  }

  @PostMapping
  public ResponseEntity<Venue> createVenue(@RequestBody CreateVenueRequest request) {
    Venue venue = venueService.create(request);
    return ResponseEntity.ok(venue);
  }

  @GetMapping
  public ResponseEntity<List<Venue>> getVenues() {
    List<Venue> venues = venueRepository.findAll();
    return ResponseEntity.ok(venues);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venue> getVenue(@PathVariable UUID id) {
    return venueRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}
