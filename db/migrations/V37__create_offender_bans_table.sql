CREATE TYPE ban_type AS ENUM ('BAN', 'TRESPASS');

CREATE TABLE IF NOT EXISTS offender_bans (
    id          UUID        NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    offender_id UUID        NOT NULL REFERENCES offenders(id) ON DELETE CASCADE,
    type        ban_type    NOT NULL,
    issued_by   TEXT        NOT NULL REFERENCES users(id),
    issued_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMPTZ
);
