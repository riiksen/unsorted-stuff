-- +micrate Up
CREATE TABLE pets (
  id INTEGER NOT NULL PRIMARY KEY,
  name VARCHAR,
  breed VARCHAR,
  age INT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);


-- +micrate Down
DROP TABLE IF EXISTS pets;
