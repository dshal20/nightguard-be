ALTER TABLE notification_subscriptions
    DROP COLUMN subscriber,
    ADD COLUMN subscriber UUID NOT NULL REFERENCES venues(id);
