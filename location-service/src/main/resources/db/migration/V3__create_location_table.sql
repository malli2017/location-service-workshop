-- Creates the table that corresponds with the Location entity
CREATE TABLE location (
  id bigserial PRIMARY KEY,
  name VARCHAR(128) NOT NULL UNIQUE,
  lat DOUBLE PRECISION NOT NULL,
  lon DOUBLE PRECISION NOT NULL
)