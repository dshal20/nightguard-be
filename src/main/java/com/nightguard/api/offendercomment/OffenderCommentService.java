package com.nightguard.api.offendercomment;

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
public class OffenderCommentService {

  private final OffenderCommentRepository offenderCommentRepository;
  private final OffenderRepository offenderRepository;
  private final UserRepository userRepository;
  private final VenueMemberRepository venueMemberRepository;

  public OffenderCommentService(
      OffenderCommentRepository offenderCommentRepository,
      OffenderRepository offenderRepository,
      UserRepository userRepository,
      VenueMemberRepository venueMemberRepository) {
    this.offenderCommentRepository = offenderCommentRepository;
    this.offenderRepository = offenderRepository;
    this.userRepository = userRepository;
    this.venueMemberRepository = venueMemberRepository;
  }

  public OffenderCommentResponse create(UUID offenderId, CreateOffenderCommentRequest request, String requestingUserId) {
    Offender offender = offenderRepository.findById(offenderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertMemberOrAdmin(offender.getVenueId(), requestingUserId);

    OffenderComment comment = new OffenderComment();
    comment.setOffenderId(offenderId);
    comment.setUserId(requestingUserId);
    comment.setComment(request.getComment());

    OffenderComment saved = offenderCommentRepository.save(comment);
    User author = userRepository.findById(requestingUserId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    return OffenderCommentResponse.from(saved, author);
  }

  public List<OffenderCommentResponse> getByOffender(UUID offenderId, String requestingUserId) {
    Offender offender = offenderRepository.findById(offenderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assertMemberOrAdmin(offender.getVenueId(), requestingUserId);

    return offenderCommentRepository.findByOffenderIdOrderByCreatedAtDesc(offenderId).stream()
        .map(c -> {
          User author = userRepository.findById(c.getUserId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
          return OffenderCommentResponse.from(c, author);
        })
        .toList();
  }

  public void delete(UUID commentId, String requestingUserId) {
    OffenderComment comment = offenderCommentRepository.findById(commentId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    Offender offender = offenderRepository.findById(comment.getOffenderId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    boolean isOwner = comment.getUserId().equals(requestingUserId);
    if (!isOwner) {
      assertManagerOrAdmin(offender.getVenueId(), requestingUserId);
    }

    offenderCommentRepository.delete(comment);
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
