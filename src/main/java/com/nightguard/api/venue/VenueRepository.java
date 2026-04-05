package com.nightguard.api.venue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, UUID> {
  Optional<Venue> findByInviteCode(String inviteCode);

  List<Venue> findByDataSharingEnabled(boolean dataSharingEnabled);
}
