INSERT INTO genres (name) VALUES
    ('Rock'),
    ('Pop'),
    ('Progressive Rock'),
    ('Grunge'),
    ('Alternative Rock'),
    ('Psychedelic Rock')
ON CONFLICT (name) DO NOTHING;

INSERT INTO albums (artist_id, title, release_year, genre_id) VALUES
    ((SELECT id FROM artists WHERE name = 'Pink Floyd'), 'The Dark Side of the Moon', 1973, (SELECT id FROM genres WHERE name = 'Progressive Rock')),
    ((SELECT id FROM artists WHERE name = 'The Beatles'), 'Abbey Road', 1969, (SELECT id FROM genres WHERE name = 'Rock')),
    ((SELECT id FROM artists WHERE name = 'Nirvana'), 'Nevermind', 1991, (SELECT id FROM genres WHERE name = 'Grunge')),
    ((SELECT id FROM artists WHERE name = 'David Bowie'), 'The Rise and Fall of Ziggy Stardust', 1972, (SELECT id FROM genres WHERE name = 'Rock')),
    ((SELECT id FROM artists WHERE name = 'Queen'), 'A Night at the Opera', 1975, (SELECT id FROM genres WHERE name = 'Rock'));

INSERT INTO tracks (album_id, track_number, title, duration_seconds) VALUES
    ((SELECT id FROM albums WHERE title = 'The Dark Side of the Moon'), 1, 'Speak to Me', 90),
    ((SELECT id FROM albums WHERE title = 'The Dark Side of the Moon'), 2, 'Breathe', 163),
    ((SELECT id FROM albums WHERE title = 'The Dark Side of the Moon'), 3, 'Time', 421),
    ((SELECT id FROM albums WHERE title = 'Abbey Road'), 1, 'Come Together', 259),
    ((SELECT id FROM albums WHERE title = 'Abbey Road'), 2, 'Something', 182),
    ((SELECT id FROM albums WHERE title = 'Nevermind'), 1, 'Smells Like Teen Spirit', 301),
    ((SELECT id FROM albums WHERE title = 'Nevermind'), 2, 'In Bloom', 254);

INSERT INTO vinyl_records (album_id, condition, price, quantity)
VALUES
    ((SELECT id FROM albums WHERE title = 'The Dark Side of the Moon'), 'Nova', 249.90, 5),
    ((SELECT id FROM albums WHERE title = 'Abbey Road'), 'Boa', 199.90, 3),
    ((SELECT id FROM albums WHERE title = 'Nevermind'), 'Aceitavel', 149.90, 4),
    ((SELECT id FROM albums WHERE title = 'The Rise and Fall of Ziggy Stardust'), 'Aceitavel', 229.90, 2),
    ((SELECT id FROM albums WHERE title = 'A Night at the Opera'), 'Nova', 189.90, 6);
