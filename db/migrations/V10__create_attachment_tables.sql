CREATE TABLE IF NOT EXISTS incident_attachments (
    id               UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    incident_id      UUID          REFERENCES incidents(id),
    uploaded_by      VARCHAR(128)  REFERENCES users(id),
    url              TEXT          NOT NULL,
    attachment_type  VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS offender_attachments (
    id               UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    offender_id      UUID          REFERENCES venue_offenders(id),
    reporter_id      VARCHAR(128)  REFERENCES users(id),
    url              TEXT,
    attachment_type  VARCHAR(50)
);
