package com.nightguard.api.patronlog;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronLogRepository extends JpaRepository<PatronLog, UUID> {
  List<PatronLog> findByVenueIdOrderByCreatedAtDesc(UUID venueId);
}
