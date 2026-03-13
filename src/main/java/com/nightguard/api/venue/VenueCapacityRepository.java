package com.nightguard.api.venue;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueCapacityRepository extends JpaRepository<VenueCapacity, UUID> {

  Optional<VenueCapacity> findByVenueId(UUID venueId);
}
