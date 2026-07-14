-- Table de base des utilisateurs (authentification).
-- Syntaxe ANSI (GENERATED ALWAYS AS IDENTITY) volontairement portable entre PostgreSQL (dev/prod) et H2 (tests).
CREATE TABLE users (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    date_creation   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
