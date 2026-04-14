CREATE TABLE patron_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    venue_id UUID NOT NULL REFERENCES venues(id),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    drivers_license_id VARCHAR(255),
    date_of_birth DATE,
    expiration_date DATE,
    state VARCHAR(10),
    street_address VARCHAR(500),
    city VARCHAR(255),
    postal_code VARCHAR(20),
    gender VARCHAR(50),
    eye_color VARCHAR(50),
    decision VARCHAR(10) NOT NULL,
    recorded_by VARCHAR(128) NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
