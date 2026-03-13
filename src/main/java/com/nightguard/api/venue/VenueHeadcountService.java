package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;

@Service
public class VenueHeadcountService {

  private final VenueHeadcountRepository venueHeadcountRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final VenueRepository venueRepository;
  private final UserRepository userRepository;

  public VenueHeadcountService(VenueHeadcountRepository venueHeadcountRepository,
      VenueMemberRepository venueMemberRepository,
      VenueRepository venueRepository,
      UserRepository userRepository) {
    this.venueHeadcountRepository = venueHeadcountRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.venueRepository = venueRepository;
    this.userRepository = userRepository;
  }

  public List<VenueHeadcount> getHeadcounts(UUID venueId, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);
    return venueHeadcountRepository.findByVenueIdOrderByCreatedAtDesc(venueId);
  }

  public VenueHeadcount addHeadcount(UUID venueId, AddHeadcountRequest request, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);

    VenueHeadcount record = new VenueHeadcount();
    record.setVenueId(venueId);
    record.setHeadcount(request.getHeadcount());
    record.setRecordedBy(requestingUserId);

    return venueHeadcountRepository.save(record);
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
}
