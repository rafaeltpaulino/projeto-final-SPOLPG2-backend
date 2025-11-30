-- 1. Tabela de Países (Domínio Básico)
CREATE TABLE countries (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Tabela de Usuários (Segurança)
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       birthdate DATE,
                       bio TEXT,
                       country_id BIGINT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_users_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

-- Tabela de ligação User <-> Role
CREATE TABLE user_roles(
                           user_id BIGINT REFERENCES users(id) NOT NULL,
                           role VARCHAR(255)
);

-- 4. Tabela de Gêneros Musicais
CREATE TABLE genres (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL UNIQUE
);

-- 5. Tabela de Gravadoras (Labels)
CREATE TABLE labels (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(150) NOT NULL,
                        foundation_date DATE NOT NULL,
                        end_date DATE,
                        bio TEXT
);

-- 6. Tabela de Artistas (Herança Single Table: Pessoa e Banda)
CREATE TABLE artists (
                         id BIGSERIAL PRIMARY KEY,
                         artist_type VARCHAR(31) NOT NULL, -- 'PERSON' ou 'GROUP'
                         name VARCHAR(150) NOT NULL,
                         description TEXT,
                         image_url VARCHAR(255),
                         country_id BIGINT,
                         start_date DATE,
                         end_date DATE,

                         CONSTRAINT fk_artists_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

-- 7. Tabela de Membros de Banda (Auto-relacionamento N:N)
CREATE TABLE group_members (
                               group_id BIGINT NOT NULL,
                               member_id BIGINT NOT NULL,
                               role VARCHAR(100),       -- Ex: "Vocals, Guitar"
                               join_date DATE,
                               leave_date DATE,
                               is_active BOOLEAN DEFAULT TRUE,

                               PRIMARY KEY (group_id, member_id),
                               CONSTRAINT fk_gm_group FOREIGN KEY (group_id) REFERENCES artists(id),
                               CONSTRAINT fk_gm_member FOREIGN KEY (member_id) REFERENCES artists(id)
);

-- 8. Tabela Master Release (A Obra)
CREATE TABLE master_releases (
                                 id BIGSERIAL PRIMARY KEY,
                                 title VARCHAR(200) NOT NULL,
                                 release_year INTEGER NOT NULL,
                                 cover_image_url VARCHAR(500),
                                 average_rating DECIMAL(3, 2) DEFAULT 0.0,
                                 description TEXT
);

-- Tabelas de Ligação da Master (N:N)
CREATE TABLE artists_masters (
                                 master_id BIGINT NOT NULL,
                                 artist_id BIGINT NOT NULL,
                                 PRIMARY KEY (master_id, artist_id),
                                 CONSTRAINT fk_am_master FOREIGN KEY (master_id) REFERENCES master_releases(id),
                                 CONSTRAINT fk_am_artist FOREIGN KEY (artist_id) REFERENCES artists(id)
);

CREATE TABLE genres_masters (
                                master_id BIGINT NOT NULL,
                                genre_id BIGINT NOT NULL,
                                PRIMARY KEY (master_id, genre_id),
                                CONSTRAINT fk_gm_master FOREIGN KEY (master_id) REFERENCES master_releases(id),
                                CONSTRAINT fk_gm_genre FOREIGN KEY (genre_id) REFERENCES genres(id)
);

-- 9. Tabela Release (A Edição Física)
CREATE TABLE releases (
                          id BIGSERIAL PRIMARY KEY,
                          master_id BIGINT NOT NULL,
                          country_id BIGINT,
                          title VARCHAR(200) NOT NULL,
                          release_date DATE,
                          format VARCHAR(100), -- LP, CD, Cassette
                          barcode VARCHAR(100),
                          is_main BOOLEAN DEFAULT FALSE,

                          CONSTRAINT fk_releases_master FOREIGN KEY (master_id) REFERENCES master_releases(id),
                          CONSTRAINT fk_releases_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

-- Tabela de Ligação Release <-> Label (Com atributos extras)
CREATE TABLE releases_labels (
                                 release_id BIGINT NOT NULL,
                                 label_id BIGINT NOT NULL,
                                 catalog_number VARCHAR(100),
                                 entity_role VARCHAR(50), -- Main Label, Distributor

                                 PRIMARY KEY (release_id, label_id),
                                 CONSTRAINT fk_rl_release FOREIGN KEY (release_id) REFERENCES releases(id),
                                 CONSTRAINT fk_rl_label FOREIGN KEY (label_id) REFERENCES labels(id)
);

-- 10. Tabela de Faixas (Tracks)
CREATE TABLE tracks (
                        id BIGSERIAL PRIMARY KEY,
                        release_id BIGINT NOT NULL,
                        title VARCHAR(200) NOT NULL,
                        position VARCHAR(20), -- A1, B2, 01, 02
                        duration_seconds INTEGER,

                        CONSTRAINT fk_tracks_release FOREIGN KEY (release_id) REFERENCES releases(id)
);

-- 11. Tabela de Reviews (Avaliações)
CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         master_id BIGINT NOT NULL,
                         rating INTEGER NOT NULL CHECK (rating >= 0 AND rating <= 5),
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(id),
                         CONSTRAINT fk_review_master FOREIGN KEY (master_id) REFERENCES master_releases(id),
    -- Regra: Um usuário só avalia um álbum uma vez
                         CONSTRAINT unique_user_review UNIQUE (user_id, master_id)
);

-- 12. Tabela de Coleção (Collection Items)
CREATE TABLE collection_items (
                                  id BIGSERIAL PRIMARY KEY,
                                  user_id BIGINT NOT NULL,
                                  release_id BIGINT NOT NULL,
                                  acquired_date DATE,
                                  media_condition VARCHAR(30), -- MINT, NEAR_MINT, VG
                                  sleeve_condition VARCHAR(30),
                                  private_notes TEXT,

                                  CONSTRAINT fk_collection_user FOREIGN KEY (user_id) REFERENCES users(id),
                                  CONSTRAINT fk_collection_release FOREIGN KEY (release_id) REFERENCES releases(id)
);