CREATE TABLE IF NOT EXISTS venue_headcount (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    venue_id UUID NOT NULL REFERENCES venues (id),
    headcount INTEGER NOT NULL,
    recorded_by VARCHAR(128) REFERENCES users (id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
