package com.nightguard.api.venue;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;

@Service
public class VenueCapacityService {

  private final VenueCapacityRepository venueCapacityRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final VenueRepository venueRepository;
  private final UserRepository userRepository;

  public VenueCapacityService(VenueCapacityRepository venueCapacityRepository,
      VenueMemberRepository venueMemberRepository,
      VenueRepository venueRepository,
      UserRepository userRepository) {
    this.venueCapacityRepository = venueCapacityRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.venueRepository = venueRepository;
    this.userRepository = userRepository;
  }

  public VenueCapacity getCapacity(UUID venueId, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);
    return venueCapacityRepository.findByVenueId(venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capacity not set for this venue"));
  }

  public VenueCapacity setCapacity(UUID venueId, SetCapacityRequest request, String requestingUserId) {
    assertVenueExists(venueId);
    assertManagerOrAdmin(venueId, requestingUserId);

    VenueCapacity record = venueCapacityRepository.findByVenueId(venueId)
        .orElseGet(VenueCapacity::new);

    record.setVenueId(venueId);
    record.setUpdatedBy(requestingUserId);
    record.setCapacity(request.getCapacity());

    return venueCapacityRepository.save(record);
  }

  private void assertVenueExists(UUID venueId) {
    venueRepository.findById(venueId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));
  }

  private void assertMemberOrAdmin(UUID venueId, String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    if (user.getRole() == Role.ADMIN) return;
    venueMemberRepository.findByVenueIdAndUserId(venueId, userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
  }

  private void assertManagerOrAdmin(UUID venueId, String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    if (user.getRole() == Role.ADMIN) return;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, userId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);
    if (!isManager) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }
}
