CREATE TABLE confirmation_codes (
    id BIGSERIAL NOT NULL,
    email TEXT NOT NULL,
    code TEXT NOT NULL,
    expiration TIMESTAMP NOT NULL,

    PRIMARY KEY (id)
);