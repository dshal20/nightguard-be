package com.nightguard.api.incident;

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
public class IncidentService {

  private final IncidentRepository incidentRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final UserRepository userRepository;

  public IncidentService(IncidentRepository incidentRepository, VenueMemberRepository venueMemberRepository,
      UserRepository userRepository) {
    this.incidentRepository = incidentRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.userRepository = userRepository;
  }

  public Incident create(CreateIncidentRequest request, String reporterId) {
    boolean isAdmin = isAdmin(reporterId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(request.getVenueId(), reporterId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    Incident incident = new Incident();
    incident.setVenueId(request.getVenueId());
    incident.setReporterId(reporterId);
    incident.setType(request.getType());
    incident.setSeverity(request.getSeverity());
    incident.setDescription(request.getDescription());
    incident.setKeywords(request.getKeywords());
    return incidentRepository.save(incident);
  }

  public List<Incident> getByVenue(UUID venueId, String requestingUserId) {
    boolean isAdmin = isAdmin(requestingUserId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    return incidentRepository.findByVenueId(venueId);
  }

  public Incident getById(UUID id, String requestingUserId) {
    Incident incident = incidentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    boolean isAdmin = isAdmin(requestingUserId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(incident.getVenueId(), requestingUserId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    return incident;
  }

  private boolean isAdmin(String userId) {
    return userRepository.findById(userId)
        .map(User::getRole)
        .map(role -> role == Role.ADMIN)
        .orElse(false);
  }
}
