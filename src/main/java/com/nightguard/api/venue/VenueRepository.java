package com.nightguard.api.venue;
import java.util.UUID;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, UUID> {
  Optional<Venue> findByInviteCode(String inviteCode);
}
