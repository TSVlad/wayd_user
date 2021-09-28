DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL NOT NULL UNIQUE,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(72) NOT NULL,

    PRIMARY KEY (id)
);

INSERT INTO users (username, password)
VALUES
    ('admin', '$2a$10$T/iTqCvA2Li1hNM6RJAo8u3.nQF2Bud2KpUz0NkMiEkXF.nHDxBsu'),
    ('moderator', '$2a$10$UNFibLE/K9aI3KIDKtgVku8M/eqgYq3.3zJgqGgady9UnWh5WFp6S'),
    ('user', '$2a$10$No9gKECUtcQFzHXPqks7xeBMcwmi8BiC1A6Vg9S05zCzmkXTvxDfq');