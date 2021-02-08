CREATE TABLE IF NOT EXISTS users
(
    id                 BIGSERIAL,
    email              VARCHAR(255) UNIQUE,
    password           VARCHAR(255)
);