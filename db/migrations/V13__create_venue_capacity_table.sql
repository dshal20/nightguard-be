CREATE TABLE IF NOT EXISTS venue_capacity (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    venue_id UUID REFERENCES venues (id),
    updated_by VARCHAR(128) REFERENCES users (id),
    capacity INTEGER NOT NULL,
    current_occupancy INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);