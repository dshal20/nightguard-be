package com.nightguard.api.offender;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;
import com.nightguard.api.venue.VenueMemberRepository;

@Service
public class OffenderService {

  private final OffenderRepository offenderRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final UserRepository userRepository;

  public OffenderService(OffenderRepository offenderRepository,
      VenueMemberRepository venueMemberRepository,
      UserRepository userRepository) {
    this.offenderRepository = offenderRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.userRepository = userRepository;
  }

  public OffenderResponse create(CreateOffenderRequest request, String requestingUserId) {
    assertMemberOrAdmin(request.getVenueId(), requestingUserId);

    Offender offender = new Offender();
    offender.setVenueId(request.getVenueId());
    offender.setFirstName(request.getFirstName());
    offender.setLastName(request.getLastName());
    offender.setPhysicalMarkers(request.getPhysicalMarkers());
    offender.setRiskScore(request.getRiskScore());
    offender.setCurrentStatus(request.getCurrentStatus());
    offender.setGlobalId(request.getGlobalId());
    offender.setNotes(request.getNotes());

    return OffenderResponse.from(offenderRepository.save(offender));
  }

  public List<OffenderResponse> getByVenue(UUID venueId, String requestingUserId) {
    assertMemberOrAdmin(venueId, requestingUserId);
    return offenderRepository.findByVenueId(venueId).stream()
        .map(OffenderResponse::from)
        .toList();
  }

  public OffenderResponse getById(UUID id, String requestingUserId) {
    Offender offender = offenderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertMemberOrAdmin(offender.getVenueId(), requestingUserId);
    return OffenderResponse.from(offender);
  }

  public OffenderResponse update(UUID id, UpdateOffenderRequest request, String requestingUserId) {
    Offender offender = offenderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertMemberOrAdmin(offender.getVenueId(), requestingUserId);

    if (request.getFirstName() != null) offender.setFirstName(request.getFirstName());
    if (request.getLastName() != null) offender.setLastName(request.getLastName());
    if (request.getPhysicalMarkers() != null) offender.setPhysicalMarkers(request.getPhysicalMarkers());
    if (request.getRiskScore() != null) offender.setRiskScore(request.getRiskScore());
    if (request.getCurrentStatus() != null) offender.setCurrentStatus(request.getCurrentStatus());
    if (request.getGlobalId() != null) offender.setGlobalId(request.getGlobalId());
    if (request.getNotes() != null) offender.setNotes(request.getNotes());

    return OffenderResponse.from(offenderRepository.save(offender));
  }

  public void delete(UUID id, String requestingUserId) {
    Offender offender = offenderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertManagerOrAdmin(offender.getVenueId(), requestingUserId);
    offenderRepository.delete(offender);
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
        .map(m -> m.getRole() == com.nightguard.api.venue.VenueRole.MANAGER)
        .orElse(false);
    if (!isManager) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }
}
