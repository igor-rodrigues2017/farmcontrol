CREATE TABLE IF NOT EXISTS farm
(
    id                 BIGSERIAL,
    farmName           VARCHAR(255) UNIQUE,
    city               VARCHAR(255),
    state              VARCHAR(255),
    country            VARCHAR(255),
    PRIMARY KEY (id)
);