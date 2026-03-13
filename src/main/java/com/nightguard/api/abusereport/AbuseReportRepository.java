package com.nightguard.api.abusereport;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbuseReportRepository extends JpaRepository<AbuseReport, UUID> {
}
