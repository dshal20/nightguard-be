CREATE TABLE IF NOT EXISTS incident_users (
    incident_id  UUID          NOT NULL REFERENCES incidents(id),
    user_id      VARCHAR(128)  NOT NULL REFERENCES users(id),
    offender_id  UUID          REFERENCES venue_offenders(id),
    type         VARCHAR(50),
    description  TEXT,
    PRIMARY KEY (incident_id, user_id)
);
