CREATE TYPE notification_type AS ENUM ('INCIDENT_REPORTED', 'OFFENDER_ADDED');

CREATE TABLE IF NOT EXISTS notifications (
    id           UUID                NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    from_venue   UUID                NOT NULL REFERENCES venues(id),
    type         notification_type   NOT NULL,
    incident_id  UUID                REFERENCES incidents(id),
    offender_id  UUID                REFERENCES offenders(id),
    created_at   TIMESTAMPTZ         NOT NULL DEFAULT NOW()
);
