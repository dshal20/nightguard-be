package com.nightguard.api.notification;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSubscriptionRepository extends JpaRepository<NotificationSubscription, UUID> {
}
