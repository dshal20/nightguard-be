package com.nightguard.api.venue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueMemberRepository extends JpaRepository<VenueMember, UUID> {
  List<VenueMember> findByVenueId(UUID venueId);
  List<VenueMember> findByUserId(String userId);
  Optional<VenueMember> findByVenueIdAndUserId(UUID venueId, String userId);
}
