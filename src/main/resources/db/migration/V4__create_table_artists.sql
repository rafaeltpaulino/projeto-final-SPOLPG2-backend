CREATE TABLE artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    debut_date DATE,
    country_id BIGINT REFERENCES countries(id),
    biography TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);