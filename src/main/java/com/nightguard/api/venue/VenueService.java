package com.nightguard.api.venue;

import org.springframework.stereotype.Service;

@Service
public class VenueService {

  private final VenueRepository venueRepository;

  public VenueService(VenueRepository venueRepository) {
    this.venueRepository = venueRepository;
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
}
