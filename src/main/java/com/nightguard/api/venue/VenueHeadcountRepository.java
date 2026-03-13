package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueHeadcountRepository extends JpaRepository<VenueHeadcount, UUID> {

  List<VenueHeadcount> findByVenueIdOrderByCreatedAtDesc(UUID venueId);
}
