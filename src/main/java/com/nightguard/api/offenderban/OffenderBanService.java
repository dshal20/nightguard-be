package com.nightguard.api.offenderban;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.offender.Offender;
import com.nightguard.api.offender.OffenderRepository;
import com.nightguard.api.user.Role;
import com.nightguard.api.user.User;
import com.nightguard.api.user.UserRepository;
import com.nightguard.api.venue.VenueMemberRepository;
import com.nightguard.api.venue.VenueRole;

@Service
public class OffenderBanService {

  private final OffenderBanRepository offenderBanRepository;
  private final OffenderRepository offenderRepository;
  private final UserRepository userRepository;
  private final VenueMemberRepository venueMemberRepository;

  public OffenderBanService(
      OffenderBanRepository offenderBanRepository,
      OffenderRepository offenderRepository,
      UserRepository userRepository,
      VenueMemberRepository venueMemberRepository) {
    this.offenderBanRepository = offenderBanRepository;
    this.offenderRepository = offenderRepository;
    this.userRepository = userRepository;
    this.venueMemberRepository = venueMemberRepository;
  }

  public OffenderBanResponse issue(UUID offenderId, IssueBanRequest request, String requestingUserId) {
    Offender offender = offenderRepository.findById(offenderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertManagerOrAdmin(offender.getVenueId(), requestingUserId);

    OffenderBan ban = new OffenderBan();
    ban.setOffenderId(offenderId);
    ban.setType(request.getType());
    ban.setIssuedBy(requestingUserId);
    ban.setExpiresAt(request.getExpiresAt());

    OffenderBan saved = offenderBanRepository.save(ban);

    String newStatus = "BAN".equals(request.getType()) ? "Banned" : "Trespassed";
    offender.setCurrentStatus(newStatus);
    offenderRepository.save(offender);

    User issuer = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    return OffenderBanResponse.from(saved, issuer);
  }

  public List<OffenderBanResponse> getByOffender(UUID offenderId, String requestingUserId) {
    Offender offender = offenderRepository.findById(offenderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertMemberOrAdmin(offender.getVenueId(), requestingUserId);

    return offenderBanRepository.findByOffenderIdOrderByIssuedAtDesc(offenderId).stream()
        .map(b -> {
          User issuer = userRepository.findById(b.getIssuedBy())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
          return OffenderBanResponse.from(b, issuer);
        })
        .toList();
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
