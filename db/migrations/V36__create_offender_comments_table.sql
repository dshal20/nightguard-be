CREATE TABLE IF NOT EXISTS offender_comments (
    id          UUID        NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    offender_id UUID        NOT NULL REFERENCES offenders(id) ON DELETE CASCADE,
    user_id     TEXT        NOT NULL REFERENCES users(id),
    comment     TEXT        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
