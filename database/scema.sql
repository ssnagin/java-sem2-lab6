DROP DATABASE IF EXISTS collection WITH (FORCE);

CREATE DATABASE collection;

\c collection

CREATE SEQUENCE secure_id_seq
    AS bigint
    START WITH 1000000000000
    INCREMENT BY 1
    MINVALUE 1000000000000
    MAXVALUE 9223372036854775807
    CACHE 100
    NO CYCLE;

CREATE OR REPLACE FUNCTION generate_secure_id()
RETURNS bigint AS $$
DECLARE
    next_val bigint;
    random_part bigint;
BEGIN
    next_val := nextval('secure_id_seq');
    random_part := floor(random() * 100000)::bigint;
    RETURN (next_val * 100000) + random_part;
END;
$$ LANGUAGE plpgsql
VOLATILE;

CREATE TABLE cm_user (
    id BIGINT PRIMARY KEY DEFAULT generate_secure_id(),
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    is_banned INTEGER NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO cm_user (username, password, is_banned)
VALUES ('admin', 'dff0de22f2bcde9b5a9b6cc632d8beb88156633e', 0);

CREATE TABLE cm_user_access (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES cm_user(id) ON DELETE CASCADE,
    accessed TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cm_collection_coordinates (
    id SERIAL PRIMARY KEY,
    x INTEGER NOT NULL,
    y INTEGER NOT NULL
);

CREATE TABLE cm_collection_album (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    tracks BIGINT
);

CREATE TABLE cm_collection (
    id BIGINT PRIMARY KEY DEFAULT generate_secure_id(),

    name TEXT NOT NULL,
    number_of_participants BIGINT NOT NULL DEFAULT 0,

    singles_count INTEGER NOT NULL DEFAULT 0,
    coordinates_id INTEGER NOT NULL REFERENCES cm_collection_coordinates(id) ON DELETE CASCADE,

    -- Специально не выносил значения, чтобы не возиться с enum'om и его обработкой. Небольшой костыль
    genre TEXT NOT NULL,
    best_album_id INTEGER REFERENCES cm_collection_album(id) ON DELETE CASCADE,

    created TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cm_user_collection (
    id BIGINT PRIMARY KEY DEFAULT generate_secure_id(),
    user_id BIGINT NOT NULL REFERENCES cm_user(id) ON DELETE CASCADE,
    collection_id BIGINT NOT NULL REFERENCES cm_collection(id) ON DELETE CASCADE
);