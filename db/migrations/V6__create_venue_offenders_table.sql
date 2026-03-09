CREATE TABLE IF NOT EXISTS venue_offenders (
    id               UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    venue_id         UUID          REFERENCES venues(id),
    first_name       VARCHAR(100),
    last_name        VARCHAR(100),
    physical_markers TEXT,
    risk_score       INTEGER,
    current_status   VARCHAR(50),
    global_id        UUID,
    notes            TEXT
);
