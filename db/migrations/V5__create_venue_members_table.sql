CREATE TABLE IF NOT EXISTS venue_members (
    id          UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    venue_id    UUID          NOT NULL REFERENCES venues(id),
    user_id     VARCHAR(128)  REFERENCES users(id),
    role        VARCHAR(50),
    created_at  TIMESTAMPTZ   NOT NULL,
    updated_at  TIMESTAMPTZ   NOT NULL
);
