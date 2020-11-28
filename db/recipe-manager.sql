-- USER and DATABASE are created automatically by setting ${POSTGRES_*} in docker-compose.yml
-- CREATE USER ${POSTGRES_USER} WITH SUPERUSER PASSWORD '${POSTGRES_PASSWORD}';

-- CREATE DATABASE ${POSTGRES_DB}
--     WITH 
--     OWNER = ${POSTGRES_USER}
--     ENCODING = 'UTF8'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1;

\c recipe-manager

CREATE SEQUENCE public.recipes_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 32767
    CACHE 1;

CREATE TABLE public.recipes
    (
    title text NOT NULL,
    ingredients text[] NOT NULL,
    description text NOT NULL,
    steps text[] NOT NULL,
    id smallint NOT NULL DEFAULT nextval('recipes_id_seq'::regclass),
    CONSTRAINT recipes_pkey PRIMARY KEY (id)
    )