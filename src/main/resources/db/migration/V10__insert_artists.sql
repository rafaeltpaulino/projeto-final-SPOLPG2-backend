INSERT INTO artists (name, debut_date, country_id, biography, artist_type) VALUES
    ('Pink Floyd', '1965-01-01', (SELECT id FROM countries WHERE name = 'Reino Unido'), 'Progressive rock band formed in London.', 'GROUP'),
    ('The Beatles', '1960-01-01', (SELECT id FROM countries WHERE name = 'Reino Unido'), 'Legendary English rock band.', 'GROUP'),
    ('Nirvana', '1987-01-01', (SELECT id FROM countries WHERE name = 'Estados Unidos'), 'American grunge band.', 'GROUP'),
    ('David Bowie', '1962-01-01', (SELECT id FROM countries WHERE name = 'Reino Unido'), 'English singer, songwriter, actor.', 'SOLO'),
    ('Queen', '1970-01-01', (SELECT id FROM countries WHERE name = 'Reino Unido'), 'British rock band from London.', 'GROUP');
