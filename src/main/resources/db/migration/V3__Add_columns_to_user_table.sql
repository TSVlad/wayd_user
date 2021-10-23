ALTER TABLE users
ADD COLUMN email TEXT;

ALTER TABLE users
ADD COLUMN contacts TEXT;


UPDATE users
SET email =  'wayd.test@gmail.com'
WHERE id IN (1, 2, 3);

ALTER TABLE users
ALTER COLUMN email SET NOT NULL;