CREATE TABLE IF NOT EXISTS incidents (
    id           UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    venue_id     UUID          REFERENCES venues(id),
    reporter_id  VARCHAR(128)  REFERENCES users(id),
    type         VARCHAR(100),
    description  TEXT,
    category     VARCHAR(100),
    severity     VARCHAR(50),
    keywords     TEXT,
    created_at   TIMESTAMPTZ   DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ   DEFAULT CURRENT_TIMESTAMP
);
