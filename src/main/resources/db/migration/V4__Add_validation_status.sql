ALTER TABLE users
ADD COLUMN valid_bad_words BOOLEAN NOT NULL DEFAULT false;

UPDATE users
SET valid_bad_words = true
WHERE id IN (1, 2, 3);