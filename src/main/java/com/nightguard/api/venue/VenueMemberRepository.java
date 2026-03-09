package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueMemberRepository extends JpaRepository<VenueMember, UUID> {
  List<VenueMember> findByVenueId(UUID venueId);
  List<VenueMember> findByUserId(String userId);
}
