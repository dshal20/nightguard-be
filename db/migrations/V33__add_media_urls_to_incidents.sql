ALTER TABLE incidents
    ADD COLUMN media_urls TEXT[] NOT NULL DEFAULT '{}';
