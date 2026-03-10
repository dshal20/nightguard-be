ALTER TABLE venues ADD COLUMN invite_code VARCHAR(6);

-- Populate existing venues with unique random codes
DO $$
DECLARE
  v RECORD;
  code VARCHAR(6);
BEGIN
  FOR v IN SELECT id FROM venues LOOP
    LOOP
      code := upper(substr(md5(random()::text), 1, 6));
      EXIT WHEN NOT EXISTS (SELECT 1 FROM venues WHERE invite_code = code);
    END LOOP;
    UPDATE venues SET invite_code = code WHERE id = v.id;
  END LOOP;
END;
$$;

ALTER TABLE venues ALTER COLUMN invite_code SET NOT NULL;
ALTER TABLE venues ADD CONSTRAINT venues_invite_code_key UNIQUE (invite_code);
