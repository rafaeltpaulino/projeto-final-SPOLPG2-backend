CREATE TABLE tracks (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL REFERENCES albums(id),
    track_number INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    duration_seconds INT,

    UNIQUE(album_id, track_number)
);
