CREATE TABLE event_to_participant (
    id BIGSERIAL NOT NULL ,
    event_id TEXT NOT NULL,
    user_id BIGINT NOT NULL,

    foreign key (user_id) references users(id),
    unique (event_id, user_id),
    primary key (id)
);