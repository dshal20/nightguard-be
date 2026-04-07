package com.nightguard.api.fcm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, UUID> {

  Optional<FcmToken> findByUserIdAndVenueId(String userId, UUID venueId);

  @Query("SELECT f.token FROM FcmToken f WHERE f.venueId = :venueId")
  List<String> findTokensByVenueId(@Param("venueId") UUID venueId);

  @Modifying
  @Transactional
  @Query("DELETE FROM FcmToken f WHERE f.token = :token")
  void deleteByToken(@Param("token") String token);
}
