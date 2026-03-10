-- Drop category column (merging into type)
ALTER TABLE incidents DROP COLUMN IF EXISTS category;

-- Convert keywords from TEXT to TEXT[]
ALTER TABLE incidents DROP COLUMN IF EXISTS keywords;
ALTER TABLE incidents ADD COLUMN keywords TEXT[];

-- Add NOT NULL constraints
ALTER TABLE incidents ALTER COLUMN venue_id SET NOT NULL;
ALTER TABLE incidents ALTER COLUMN reporter_id SET NOT NULL;
ALTER TABLE incidents ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE incidents ALTER COLUMN updated_at SET NOT NULL;

-- Add check constraints for enum-like values
ALTER TABLE incidents ADD CONSTRAINT incidents_type_check
    CHECK (type IN (
        'VERBAL_HARASSMENT',
        'SEXUAL_HARASSMENT',
        'PHYSICAL_ASSAULT',
        'THREAT',
        'STALKING',
        'THEFT',
        'DRUG_RELATED',
        'TRESPASSING',
        'DISORDERLY_CONDUCT',
        'VANDALISM',
        'OTHER'
    ));

ALTER TABLE incidents ADD CONSTRAINT incidents_severity_check
    CHECK (severity IN ('LOW', 'MEDIUM', 'HIGH'));
