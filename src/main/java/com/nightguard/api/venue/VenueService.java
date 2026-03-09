package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VenueService {

  private final VenueRepository venueRepository;
  private final VenueMemberRepository venueMemberRepository;

  public VenueService(VenueRepository venueRepository, VenueMemberRepository venueMemberRepository) {
    this.venueRepository = venueRepository;
    this.venueMemberRepository = venueMemberRepository;
  }

  public Venue create(CreateVenueRequest request) {
    Venue venue = new Venue();
    venue.setName(request.getName());
    venue.setStreetAddress(request.getStreetAddress());
    venue.setCity(request.getCity());
    venue.setState(request.getState());
    venue.setPostalCode(request.getPostalCode());
    venue.setPhoneNumber(request.getPhoneNumber());
    return venueRepository.save(venue);
  }

  public List<Venue> getAllForUser(String userId) {
    List<UUID> venueIds = venueMemberRepository.findByUserId(userId).stream()
        .map(VenueMember::getVenueId)
        .toList();
    return venueRepository.findAllById(venueIds);
  }

  public Venue getById(UUID id) {
    return venueRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<VenueMember> getMembers(UUID venueId) {
    getById(venueId); // ensures 404 if venue doesn't exist
    return venueMemberRepository.findByVenueId(venueId);
  }
}
