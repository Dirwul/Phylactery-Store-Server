create table if not exists users (
    id uuid primary key default gen_random_uuid(),
    username varchar(255) not null,
    password varchar(255) not null,
    token varchar(255) not null unique
);