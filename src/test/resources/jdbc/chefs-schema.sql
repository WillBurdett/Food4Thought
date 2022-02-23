DROP TABLE IF EXISTS chefs;
CREATE TABLE chefs (
    id SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL,
    email text NOT NULL,
    location character varying(255) NOT NULL,
    price numeric(10,2) NOT NULL
);