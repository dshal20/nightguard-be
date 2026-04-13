ALTER TABLE notification_subscriptions
    ADD COLUMN notification_level VARCHAR(16) NOT NULL DEFAULT 'LOW';
