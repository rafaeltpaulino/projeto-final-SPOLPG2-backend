-- 1. Criar Usuário ADMIN
-- Senha 'admin123' (hash BCrypt gerado para exemplo)
INSERT INTO users (username, email, password, first_name, last_name, country_id)
VALUES ('admin', 'admin@vinylmanager.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAGeW.f8i8.s.w.Z.w.w.w.w.w.w', 'System', 'Administrator',
        (SELECT id FROM countries WHERE name = 'Brasil'))
    ON CONFLICT (username) DO NOTHING;

-- Atribuir Role ADMIN
INSERT INTO user_roles (user_id, role)
VALUES ((SELECT id FROM users WHERE username = 'admin'), 'ADMIN');


-- 2. Inserir GÊNEROS
INSERT INTO genres (name) VALUES
                              ('Rock'),
                              ('Pop'),
                              ('MPB'),
                              ('Jazz'),
                              ('Electronic')
    ON CONFLICT (name) DO NOTHING;


-- 3. Inserir LABELS (Gravadoras)
INSERT INTO labels (name, foundation_date, bio) VALUES
                                                    ('Parlophone', '1896-01-01', 'Gravadora histórica britânica, famosa pelos Beatles.'),
                                                    ('Columbia', '1889-01-15', 'Uma das gravadoras mais antigas do mundo.'),
                                                    ('Philips', '1950-01-01', 'Selo importante para a MPB.'),
                                                    ('Daft Life', '2000-01-01', 'Selo próprio do Daft Punk.')
    ON CONFLICT DO NOTHING;


-- 4. Inserir ARTISTAS (2 Solos, 2 Bandas)

-- David Bowie (Solo)
INSERT INTO artists (artist_type, name, description, country_id, start_date)
VALUES ('PERSON', 'David Bowie', 'O Camaleão do Rock.',
        (SELECT id FROM countries WHERE name = 'Reino Unido'), '1947-01-08');

-- Elis Regina (Solo)
INSERT INTO artists (artist_type, name, description, country_id, start_date)
VALUES ('PERSON', 'Elis Regina', 'Uma das maiores cantoras da música brasileira.',
        (SELECT id FROM countries WHERE name = 'Brasil'), '1945-03-17');

-- The Beatles (Banda)
INSERT INTO artists (artist_type, name, description, country_id, start_date)
VALUES ('GROUP', 'The Beatles', 'A banda mais influente de todos os tempos.',
        (SELECT id FROM countries WHERE name = 'Reino Unido'), '1960-01-01');

-- Daft Punk (Banda)
INSERT INTO artists (artist_type, name, description, country_id, start_date, end_date)
VALUES ('GROUP', 'Daft Punk', 'Duo francês de música eletrônica.',
        (SELECT id FROM countries WHERE name = 'França'), '1993-01-01', '2021-02-22');


-- 5. Inserir MASTER RELEASES (Obras)

-- Bowie: Heroes
INSERT INTO master_releases (title, release_year, description)
VALUES ('Heroes', 1977, 'Segundo álbum da "Trilogia de Berlim".');

-- Elis: Falso Brilhante
INSERT INTO master_releases (title, release_year, description)
VALUES ('Falso Brilhante', 1976, 'Álbum clássico da MPB.');

-- Beatles: Abbey Road
INSERT INTO master_releases (title, release_year, description)
VALUES ('Abbey Road', 1969, 'O último álbum gravado pela banda.');

-- Daft Punk: Discovery
INSERT INTO master_releases (title, release_year, description)
VALUES ('Discovery', 2001, 'Álbum que definiu a house music dos anos 2000.');


-- 6. Vínculos MASTER <-> ARTISTA & GÊNERO

-- Heroes (Bowie, Rock)
INSERT INTO artists_masters (master_id, artist_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Heroes'), (SELECT id FROM artists WHERE name = 'David Bowie'));
INSERT INTO genres_masters (master_id, genre_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Heroes'), (SELECT id FROM genres WHERE name = 'Rock'));

-- Falso Brilhante (Elis, MPB)
INSERT INTO artists_masters (master_id, artist_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Falso Brilhante'), (SELECT id FROM artists WHERE name = 'Elis Regina'));
INSERT INTO genres_masters (master_id, genre_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Falso Brilhante'), (SELECT id FROM genres WHERE name = 'MPB'));

-- Abbey Road (Beatles, Rock)
INSERT INTO artists_masters (master_id, artist_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Abbey Road'), (SELECT id FROM artists WHERE name = 'The Beatles'));
INSERT INTO genres_masters (master_id, genre_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Abbey Road'), (SELECT id FROM genres WHERE name = 'Rock'));

-- Discovery (Daft Punk, Electronic)
INSERT INTO artists_masters (master_id, artist_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Discovery'), (SELECT id FROM artists WHERE name = 'Daft Punk'));
INSERT INTO genres_masters (master_id, genre_id)
VALUES ((SELECT id FROM master_releases WHERE title = 'Discovery'), (SELECT id FROM genres WHERE name = 'Electronic'));


-- 7. Inserir RELEASES (Edições Físicas) com Labels e Tracks

-- --- HEROES (LP UK) ---
INSERT INTO releases (master_id, country_id, title, release_date, format, barcode, is_main)
VALUES ((SELECT id FROM master_releases WHERE title = 'Heroes'), (SELECT id FROM countries WHERE name = 'Reino Unido'),
        'Heroes (UK Original)', '1977-10-14', 'LP', '085698500', true);

INSERT INTO releases_labels (release_id, label_id, catalog_number, entity_role)
VALUES ((SELECT MAX(id) FROM releases), (SELECT id FROM labels WHERE name = 'Parlophone'), 'PL 12522', 'Main Label');

INSERT INTO tracks (release_id, title, position, duration_seconds) VALUES
                                                                       ((SELECT MAX(id) FROM releases), 'Beauty and the Beast', 'A1', 212),
                                                                       ((SELECT MAX(id) FROM releases), 'Joe the Lion', 'A2', 185),
                                                                       ((SELECT MAX(id) FROM releases), 'Heroes', 'A3', 370);


-- --- FALSO BRILHANTE (LP BR) ---
INSERT INTO releases (master_id, country_id, title, release_date, format, barcode, is_main)
VALUES ((SELECT id FROM master_releases WHERE title = 'Falso Brilhante'), (SELECT id FROM countries WHERE name = 'Brasil'),
        'Falso Brilhante (LP)', '1976-01-01', 'LP', '789123456', true);

INSERT INTO releases_labels (release_id, label_id, catalog_number, entity_role)
VALUES ((SELECT MAX(id) FROM releases), (SELECT id FROM labels WHERE name = 'Philips'), '6349.183', 'Main Label');

INSERT INTO tracks (release_id, title, position, duration_seconds) VALUES
                                                                       ((SELECT MAX(id) FROM releases), 'Como Nossos Pais', 'A1', 265),
                                                                       ((SELECT MAX(id) FROM releases), 'Velha Roupa Colorida', 'A2', 240);


-- --- ABBEY ROAD (LP UK) ---
INSERT INTO releases (master_id, country_id, title, release_date, format, barcode, is_main)
VALUES ((SELECT id FROM master_releases WHERE title = 'Abbey Road'), (SELECT id FROM countries WHERE name = 'Reino Unido'),
        'Abbey Road (1st Press)', '1969-09-26', 'LP', '07777464461', true);

INSERT INTO releases_labels (release_id, label_id, catalog_number, entity_role)
VALUES ((SELECT MAX(id) FROM releases), (SELECT id FROM labels WHERE name = 'Parlophone'), 'PCS 7088', 'Main Label');

INSERT INTO tracks (release_id, title, position, duration_seconds) VALUES
                                                                       ((SELECT MAX(id) FROM releases), 'Come Together', 'A1', 259),
                                                                       ((SELECT MAX(id) FROM releases), 'Something', 'A2', 183);


-- --- DISCOVERY (CD FR) ---
INSERT INTO releases (master_id, country_id, title, release_date, format, barcode, is_main)
VALUES ((SELECT id FROM master_releases WHERE title = 'Discovery'), (SELECT id FROM countries WHERE name = 'França'),
        'Discovery (CD)', '2001-03-12', 'CD', '72438496062', true);

INSERT INTO releases_labels (release_id, label_id, catalog_number, entity_role)
VALUES ((SELECT MAX(id) FROM releases), (SELECT id FROM labels WHERE name = 'Daft Life'), 'VISA 6025', 'Main Label');

INSERT INTO tracks (release_id, title, position, duration_seconds) VALUES
                                                                       ((SELECT MAX(id) FROM releases), 'One More Time', '1', 320),
                                                                       ((SELECT MAX(id) FROM releases), 'Aerodynamic', '2', 207),
                                                                       ((SELECT MAX(id) FROM releases), 'Digital Love', '3', 298);