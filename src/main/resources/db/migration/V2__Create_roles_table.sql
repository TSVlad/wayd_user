DROP TABLE IF EXISTS roles;

CREATE TABLE roles(
    id BIGSERIAL,
    name TEXT NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES
        ('ROLE_USER'),
        ('ROLE_PERSON'),
        ('ROLE_ORGANIZATION'),
        ('ROLE_MODERATOR'),
        ('ROLE_ADMIN');




DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL ,
    FOREIGN KEY (user_id) references users(id),
    FOREIGN KEY (role_id) references roles(id)
);

INSERT INTO users_roles(user_id, role_id)
VALUES
        (1, 1),
        (1, 2),
        (1, 4),
        (1, 5),
        (2, 1),
        (2, 2),
        (2, 4),
        (3, 1),
        (3, 2);
