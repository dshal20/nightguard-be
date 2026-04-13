package com.nightguard.api.notification;

import java.util.UUID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSubscriptionRepository extends JpaRepository<NotificationSubscription, UUID> {
  List<NotificationSubscription> findByVenueId(UUID venueId);

  List<NotificationSubscription> findBySubscriberOrderByCreatedAtAscIdAsc(UUID subscriber);

  java.util.Optional<NotificationSubscription> findBySubscriberAndVenueId(UUID subscriber, UUID venueId);
}
