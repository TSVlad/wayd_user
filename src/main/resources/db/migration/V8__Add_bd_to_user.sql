ALTER TABLE users
ADD COLUMN date_of_birth DATE;

UPDATE users
SET date_of_birth = '1990-01-10'
WHERE id IN (1, 2, 3);

ALTER TABLE users
ALTER COLUMN date_of_birth SET NOT NULL;
