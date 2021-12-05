ALTER TABLE users
ADD COLUMN name TEXT;

ALTER TABLE users
    ADD COLUMN surname TEXT;

UPDATE users
SET surname = 'Test'
WHERE id IN (1, 2, 3);

UPDATE users
SET name = 'Admin'
WHERE id = 1;

UPDATE users
SET name = 'Moderator'
WHERE id = 2;

UPDATE users
SET name = 'User'
WHERE id = 3;