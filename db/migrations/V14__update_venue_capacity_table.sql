ALTER TABLE venue_capacity
DROP COLUMN current_occupancy;

ALTER TABLE venue_capacity
ADD CONSTRAINT venue_capacity_venue_id_key UNIQUE (venue_id);
