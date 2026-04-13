ALTER TABLE notification_subscriptions
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT NOW();
