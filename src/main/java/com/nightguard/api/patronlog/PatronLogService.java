package com.nightguard.api.patronlog;

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
import com.nightguard.api.venue.VenueMemberRepository;
import com.nightguard.api.venue.VenueRepository;

@Service
public class PatronLogService {

  private final PatronLogRepository patronLogRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final VenueRepository venueRepository;
  private final UserRepository userRepository;

  public PatronLogService(PatronLogRepository patronLogRepository,
      VenueMemberRepository venueMemberRepository,
      VenueRepository venueRepository,
      UserRepository userRepository) {
    this.patronLogRepository = patronLogRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.venueRepository = venueRepository;
    this.userRepository = userRepository;
  }

  public PatronLogResponse addPatronLog(UUID venueId, CreatePatronLogRequest request, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);

    PatronLog log = new PatronLog();
    log.setVenueId(venueId);
    log.setFirstName(request.getFirstName());
    log.setLastName(request.getLastName());
    log.setMiddleName(request.getMiddleName());
    log.setDriversLicenseId(request.getDriversLicenseId());
    log.setDateOfBirth(request.getDateOfBirth());
    log.setExpirationDate(request.getExpirationDate());
    log.setState(request.getState());
    log.setStreetAddress(request.getStreetAddress());
    log.setCity(request.getCity());
    log.setPostalCode(request.getPostalCode());
    log.setGender(request.getGender());
    log.setEyeColor(request.getEyeColor());
    log.setDecision(request.getDecision());
    log.setRecordedBy(requestingUserId);

    PatronLog saved = patronLogRepository.save(log);
    User user = userRepository.findById(requestingUserId).orElse(null);
    return PatronLogResponse.from(saved, user);
  }

  public List<PatronLogResponse> getPatronLogs(UUID venueId, String requestingUserId) {
    assertVenueExists(venueId);
    assertMemberOrAdmin(venueId, requestingUserId);

    List<PatronLog> logs = patronLogRepository.findByVenueIdOrderByCreatedAtDesc(venueId);

    Set<String> userIds = logs.stream()
        .map(PatronLog::getRecordedBy)
        .filter(id -> id != null)
        .collect(Collectors.toSet());

    Map<String, User> usersById = userRepository.findAllById(userIds).stream()
        .collect(Collectors.toMap(User::getId, u -> u));

    return logs.stream()
        .map(l -> PatronLogResponse.from(l, usersById.get(l.getRecordedBy())))
        .toList();
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
