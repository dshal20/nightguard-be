package com.nightguard.api.user;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nightguard.api.dto.UpdateUserRequest;
import com.nightguard.api.fcm.FcmToken;
import com.nightguard.api.fcm.FcmTokenRepository;
import com.nightguard.api.venue.VenueMember;
import com.nightguard.api.venue.VenueMemberRepository;
import com.nightguard.api.venue.VenueRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final FcmTokenRepository fcmTokenRepository;
  private final VenueMemberRepository venueMemberRepository;
  private final VenueRepository venueRepository;

  public UserService(UserRepository userRepository,
      FcmTokenRepository fcmTokenRepository,
      VenueMemberRepository venueMemberRepository,
      VenueRepository venueRepository) {
    this.userRepository = userRepository;
    this.fcmTokenRepository = fcmTokenRepository;
    this.venueMemberRepository = venueMemberRepository;
    this.venueRepository = venueRepository;
  }

  public User findOrCreate(String uid, String email) {
    return userRepository.findById(uid).orElseGet(() -> {
      User user = new User();
      user.setId(uid);
      user.setEmail(email);
      return userRepository.save(user);
    });
  }

  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public User update(String uid, String email, UpdateUserRequest request) {
    User user = findOrCreate(uid, email);
    if (request.getFirstName() != null)
      user.setFirstName(request.getFirstName());
    if (request.getLastName() != null)
      user.setLastName(request.getLastName());
    if (request.getEmail() != null)
      user.setEmail(request.getEmail());
    if (request.getPhoneNumber() != null)
      user.setPhoneNumber(request.getPhoneNumber());
    if (request.getProfileUrl() != null)
      user.setProfileUrl(request.getProfileUrl());
    return userRepository.save(user);
  }

  /**
   * Upserts an FCM token for each venue the user belongs to.
   * Admins are not venue members but have access to all venues, so their
   * token is registered against every venue in the system.
   * One row per (user, venue) pair — all rows share the same device token.
   */
  public void updateFcmToken(String uid, String fcmToken) {
    User user = userRepository.findById(uid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    List<UUID> venueIds;
    if (user.getRole() == Role.ADMIN) {
      venueIds = venueRepository.findAll().stream()
          .map(v -> v.getId())
          .toList();
    } else {
      venueIds = venueMemberRepository.findByUserId(uid).stream()
          .map(VenueMember::getVenueId)
          .toList();
    }

    for (UUID venueId : venueIds) {
      FcmToken record = fcmTokenRepository
          .findByUserIdAndVenueId(uid, venueId)
          .orElseGet(() -> {
            FcmToken t = new FcmToken();
            t.setUserId(uid);
            t.setVenueId(venueId);
            return t;
          });
      record.setToken(fcmToken);
      fcmTokenRepository.save(record);
    }
  }
}
