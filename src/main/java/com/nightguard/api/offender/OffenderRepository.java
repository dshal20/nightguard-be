package com.nightguard.api.offender;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OffenderRepository extends JpaRepository<Offender, UUID> {

  List<Offender> findByVenueId(UUID venueId);
}
