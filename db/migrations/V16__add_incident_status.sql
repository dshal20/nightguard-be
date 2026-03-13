CREATE TYPE incident_status AS ENUM ('Active', 'Closed');

ALTER TABLE incidents
ADD COLUMN status incident_status NOT NULL DEFAULT 'Active';
