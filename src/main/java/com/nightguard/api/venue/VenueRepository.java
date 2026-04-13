package com.nightguard.api.venue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, UUID> {
  Optional<Venue> findByInviteCode(String inviteCode);

  List<Venue> findAllByOrderByCreatedAtAscIdAsc();

  List<Venue> findByIdInOrderByCreatedAtAscIdAsc(Collection<UUID> ids);

  List<Venue> findByDataSharingEnabled(boolean dataSharingEnabled);

  List<Venue> findByDataSharingEnabledAndCityIgnoreCaseAndStateIgnoreCase(boolean dataSharingEnabled, String city, String state);

  List<Venue> findByDataSharingEnabledAndCityIgnoreCaseAndStateIgnoreCaseAndPostalCode(boolean dataSharingEnabled, String city, String state, String postalCode);
}
