package com.nightguard.api.incident;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

  List<Incident> findByVenueId(UUID venueId);

  @Query(value = "SELECT * FROM incidents WHERE CAST(:offenderId AS uuid) = ANY(offender_ids)", nativeQuery = true)
  List<Incident> findByOffenderId(@Param("offenderId") UUID offenderId);
}
