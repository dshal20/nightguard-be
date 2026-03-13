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

  public IncidentResponse create(CreateIncidentRequest request, String reporterId) {
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
    incident.setStatus(request.getStatus());
    return toResponse(incidentRepository.save(incident));
  }

  public List<IncidentResponse> getByVenue(UUID venueId, String requestingUserId) {
    boolean isAdmin = isAdmin(requestingUserId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(venueId, requestingUserId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    return incidentRepository.findByVenueId(venueId).stream()
        .map(this::toResponse)
        .toList();
  }

  public IncidentResponse update(UUID id, UpdateIncidentRequest request, String requestingUserId) {
    Incident incident = incidentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    boolean isAdmin = isAdmin(requestingUserId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(incident.getVenueId(), requestingUserId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    if (request.getType() != null) incident.setType(request.getType());
    if (request.getSeverity() != null) incident.setSeverity(request.getSeverity());
    if (request.getDescription() != null) incident.setDescription(request.getDescription());
    if (request.getKeywords() != null) incident.setKeywords(request.getKeywords());
    if (request.getStatus() != null) incident.setStatus(request.getStatus());

    return toResponse(incidentRepository.save(incident));
  }

  public IncidentResponse getById(UUID id, String requestingUserId) {
    Incident incident = incidentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    boolean isAdmin = isAdmin(requestingUserId);
    boolean isMember = venueMemberRepository.findByVenueIdAndUserId(incident.getVenueId(), requestingUserId).isPresent();
    if (!isAdmin && !isMember) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    return toResponse(incident);
  }

  private IncidentResponse toResponse(Incident incident) {
    User reporter = userRepository.findById(incident.getReporterId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    return IncidentResponse.from(incident, reporter);
  }

  private boolean isAdmin(String userId) {
    return userRepository.findById(userId)
        .map(User::getRole)
        .map(role -> role == Role.ADMIN)
        .orElse(false);
  }
}
