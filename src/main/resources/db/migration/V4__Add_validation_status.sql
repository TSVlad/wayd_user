ALTER TABLE users
ADD COLUMN valid_bad_words TEXT NOT NULL DEFAULT 'NOT_VALIDATED';

UPDATE users
SET valid_bad_words = 'VALID'
WHERE id IN (1, 2, 3);