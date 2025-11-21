CREATE TABLE users(
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(25) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birthdate DATE,
  country_id BIGINT REFERENCES countries(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  bio TEXT
);