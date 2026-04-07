ALTER TABLE users DROP COLUMN IF EXISTS fcm_token;

CREATE TABLE fcm_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    venue_id UUID NOT NULL REFERENCES venues(id) ON DELETE CASCADE,
    fcm_token VARCHAR(512) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_fcm_tokens_user_venue UNIQUE (user_id, venue_id)
);

CREATE INDEX idx_fcm_tokens_venue_id ON fcm_tokens(venue_id);
