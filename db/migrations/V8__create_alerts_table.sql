CREATE TABLE IF NOT EXISTS alerts (
    id           UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    incident_id  UUID          REFERENCES incidents(id),
    offender_id  UUID          REFERENCES venue_offenders(id),
    alert_type   VARCHAR(100),
    zone         VARCHAR(100),
    created_at   TIMESTAMPTZ   DEFAULT CURRENT_TIMESTAMP
);
