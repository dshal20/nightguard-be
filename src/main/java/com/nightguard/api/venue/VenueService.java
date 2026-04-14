package com.nightguard.api.venue;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;

@Service
public class VenueService {

  private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();

  private String generateUniqueInviteCode() {
    String code;
    do {
      StringBuilder sb = new StringBuilder(6);
      for (int i = 0; i < 6; i++) {
        sb.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
      }
      code = sb.toString();
    } while (venueRepository.findByInviteCode(code).isPresent());
    return code;
  }

  private final VenueRepository venueRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final UserRepository userRepository;

  public VenueService(VenueRepository venueRepository, VenueMemberRepository venueMemberRepository,
      UserRepository userRepository) {
    this.venueRepository = venueRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.userRepository = userRepository;
  }

  public Venue create(CreateVenueRequest request) {
    Venue venue = new Venue();
    venue.setName(request.getName());
    venue.setStreetAddress(request.getStreetAddress());
    venue.setCity(request.getCity());
    venue.setState(request.getState());
    venue.setPostalCode(request.getPostalCode());
    venue.setPhoneNumber(request.getPhoneNumber());
    venue.setVenueImageUrl(request.getVenueImageUrl());
    venue.setInviteCode(generateUniqueInviteCode());
    return venueRepository.save(venue);
  }

  public List<Venue> getAllForUser(String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    if (user.getRole() == Role.ADMIN) {
      return venueRepository.findAllByOrderByCreatedAtAscIdAsc();
    }

    List<UUID> venueIds = venueMemberRepository.findByUserId(userId).stream()
        .map(VenueMember::getVenueId)
        .toList();
    return venueRepository.findByIdInOrderByCreatedAtAscIdAsc(venueIds);
  }

  public Venue getById(UUID id) {
    return venueRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<Venue> getNearbyVenues(String city, String state, String zip, UUID venueId) {
    List<Venue> venues = (zip != null)
        ? venueRepository.findByDataSharingEnabledAndCityIgnoreCaseAndStateIgnoreCaseAndPostalCode(true, city, state, zip)
        : venueRepository.findByDataSharingEnabledAndCityIgnoreCaseAndStateIgnoreCase(true, city, state);

    return venues.stream()
        .filter(v -> !v.getId().equals(venueId))
        .toList();
  }

  public List<VenueMember> getMembers(UUID venueId) {
    getById(venueId);
    return venueMemberRepository.findByVenueId(venueId);
  }

  public List<VenueMember> addMembers(UUID venueId, List<AddVenueMemberRequest> requests, String requestingUserId) {
    User requestingUser = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    boolean isAdmin = requestingUser.getRole() == Role.ADMIN;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);

    if (!isAdmin && !isManager) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    getById(venueId);

    List<VenueMember> members = requests.stream().map(req -> {
      VenueMember member = new VenueMember();
      member.setVenueId(venueId);
      member.setUserId(req.getUserId());
      member.setRole(req.getRole());
      return member;
    }).toList();

    return venueMemberRepository.saveAll(members);
  }

  public void removeMember(UUID venueId, String targetUserId, String requestingUserId) {
    User requestingUser = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    boolean isAdmin = requestingUser.getRole() == Role.ADMIN;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);

    if (!isAdmin && !isManager) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    VenueMember member = venueMemberRepository.findByVenueIdAndUserId(venueId, targetUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    venueMemberRepository.delete(member);
  }

  public VenueMember joinByInviteCode(String inviteCode, String userId) {
    Venue venue = venueRepository.findByInviteCode(inviteCode)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid invite code"));

    if (venueMemberRepository.findByVenueIdAndUserId(venue.getId(), userId).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Already a member of this venue");
    }

    VenueMember member = new VenueMember();
    member.setVenueId(venue.getId());
    member.setUserId(userId);
    member.setRole(VenueRole.MEMBER);
    return venueMemberRepository.save(member);
  }

  public Venue update(UUID venueId, UpdateVenueRequest request, String requestingUserId) {
    User requestingUser = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    boolean isAdmin = requestingUser.getRole() == Role.ADMIN;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);

    if (!isAdmin && !isManager) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    Venue venue = getById(venueId);
    if (request.getName() != null) venue.setName(request.getName());
    if (request.getStreetAddress() != null) venue.setStreetAddress(request.getStreetAddress());
    if (request.getCity() != null) venue.setCity(request.getCity());
    if (request.getState() != null) venue.setState(request.getState());
    if (request.getPostalCode() != null) venue.setPostalCode(request.getPostalCode());
    if (request.getPhoneNumber() != null) venue.setPhoneNumber(request.getPhoneNumber());
    if (request.getVenueImageUrl() != null) venue.setVenueImageUrl(request.getVenueImageUrl());
    return venueRepository.save(venue);
  }

  public Venue updateDataSharing(UUID venueId, boolean enabled, String requestingUserId) {
    User requestingUser = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    boolean isAdmin = requestingUser.getRole() == Role.ADMIN;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);

    if (!isAdmin && !isManager) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    Venue venue = getById(venueId);
    venue.setDataSharingEnabled(enabled);
    return venueRepository.save(venue);
  }

  public VenueMember updateMemberRole(UUID venueId, String targetUserId, UpdateMemberRoleRequest request,
      String requestingUserId) {
    User requestingUser = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    boolean isAdmin = requestingUser.getRole() == Role.ADMIN;
    boolean isManager = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId)
        .map(m -> m.getRole() == VenueRole.MANAGER)
        .orElse(false);

    if (!isAdmin && !isManager) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    VenueMember member = venueMemberRepository.findByVenueIdAndUserId(venueId, targetUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    member.setRole(request.getRole());
    return venueMemberRepository.save(member);
  }
}
