ALTER TABLE offenders
    ADD COLUMN photo_urls TEXT[] NOT NULL DEFAULT '{}';
