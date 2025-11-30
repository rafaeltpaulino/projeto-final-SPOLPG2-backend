CREATE TABLE albums (
    id BIGSERIAL PRIMARY KEY,
    artist_id BIGINT NOT NULL REFERENCES artists(id),
    title VARCHAR(200) NOT NULL,
    release_year INT,
    genre_id BIGINT REFERENCES genres(id),
    created_at TIMESTAMP DEFAULT NOW()
);
