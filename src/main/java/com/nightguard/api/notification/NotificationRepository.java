package com.nightguard.api.notification;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
  List<Notification> findByFromVenueInOrderByCreatedAtDesc(Collection<UUID> venueIds);
}
