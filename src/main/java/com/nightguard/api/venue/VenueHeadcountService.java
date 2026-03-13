package com.nightguard.api.venue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

  public List<VenueHeadcountResponse> getHeadcounts(UUID venueId, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);

    List<VenueHeadcount> records = venueHeadcountRepository.findByVenueIdOrderByCreatedAtAsc(venueId);

    Set<String> userIds = records.stream()
        .map(VenueHeadcount::getRecordedBy)
        .filter(id -> id != null)
        .collect(Collectors.toSet());

    Map<String, User> usersById = userRepository.findAllById(userIds).stream()
        .collect(Collectors.toMap(User::getId, u -> u));

    return records.stream()
        .map(r -> VenueHeadcountResponse.from(r, usersById.get(r.getRecordedBy())))
        .toList();
  }

  public VenueHeadcountResponse addHeadcount(UUID venueId, AddHeadcountRequest request, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);

    VenueHeadcount record = new VenueHeadcount();
    record.setVenueId(venueId);
    record.setHeadcount(request.getHeadcount());
    record.setRecordedBy(requestingUserId);

    VenueHeadcount saved = venueHeadcountRepository.save(record);
    User user = userRepository.findById(requestingUserId).orElse(null);
    return VenueHeadcountResponse.from(saved, user);
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
