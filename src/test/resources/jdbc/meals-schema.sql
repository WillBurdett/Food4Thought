DROP TABLE IF EXISTS meals;
CREATE TABLE meals (
    id SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL,
    allergy_info character varying(255),
    difficulty character varying(255) NOT NULL,
    ingredients character varying(255) NOT NULL,
    steps text NOT NULL,
    meal_time character varying(255) NOT NULL
);