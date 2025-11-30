CREATE TABLE vinyl_records (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL REFERENCES albums(id),
    condition VARCHAR(50),        -- ex: Nova, Boa, Aceitável
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT NOW()
);
