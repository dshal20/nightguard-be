package com.nightguard.api.offenderban;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OffenderBanRepository extends JpaRepository<OffenderBan, UUID> {
  List<OffenderBan> findByOffenderIdOrderByIssuedAtDesc(UUID offenderId);
}
