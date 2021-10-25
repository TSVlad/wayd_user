UPDATE users
SET email = 'wayd.admtest@gmail.com'
WHERE id = 1;

UPDATE users
SET email = 'wayd.moderator@yandex.ru'
WHERE id = 2;


ALTER TABLE users
ADD UNIQUE (email);

ALTER TABLE users
ADD UNIQUE (username);