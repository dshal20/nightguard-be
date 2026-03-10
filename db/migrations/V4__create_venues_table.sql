CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS venues (
    id                 UUID          PRIMARY KEY DEFAULT uuid_generate_v4(),
    name               VARCHAR(255)  NOT NULL,
    street_address     TEXT,
    city               VARCHAR(100),
    state              VARCHAR(50),
    zip                VARCHAR(20),
    phone_number       VARCHAR(20),
    associated_venues  UUID[],
    created_at         TIMESTAMPTZ   NOT NULL,
    updated_at         TIMESTAMPTZ   NOT NULL
);
