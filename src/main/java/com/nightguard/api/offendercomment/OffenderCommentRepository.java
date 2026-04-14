package com.nightguard.api.offendercomment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OffenderCommentRepository extends JpaRepository<OffenderComment, UUID> {
  List<OffenderComment> findByOffenderIdOrderByCreatedAtDesc(UUID offenderId);
}
