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
public class VenueService {

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
    getById(venueId);
    return venueMemberRepository.findByVenueId(venueId);
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
