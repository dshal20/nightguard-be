CREATE TABLE IF NOT EXISTS notification_subscriptions (
    id           UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    subscriber   VARCHAR(128)  NOT NULL REFERENCES users(id),
    venue_id     UUID          NOT NULL REFERENCES venues(id)
);
