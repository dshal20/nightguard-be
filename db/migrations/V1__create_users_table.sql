CREATE TABLE IF NOT EXISTS users (
    id            VARCHAR(128)  PRIMARY KEY,
    first_name    VARCHAR(255)  NOT NULL,
    last_name     VARCHAR(255)  NOT NULL,
    email         VARCHAR(255)  NOT NULL UNIQUE,
    phone_number  VARCHAR(20),
    created_at    TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);
