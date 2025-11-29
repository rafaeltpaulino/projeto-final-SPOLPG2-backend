ALTER TABLE artists ADD COLUMN artist_type VARCHAR(20) DEFAULT 'GROUP';

CREATE TABLE group_members (
                               group_id BIGINT NOT NULL,
                               member_id BIGINT NOT NULL,

                               role VARCHAR(100),       -- Ex: "Vocals, Guitar"
                               join_date DATE,          -- Quando entrou
                               leave_date DATE,         -- Quando saiu (null se ainda estiver)
                               is_active BOOLEAN DEFAULT TRUE,

                               CONSTRAINT fk_gm_group FOREIGN KEY (group_id) REFERENCES artists(id),
                               CONSTRAINT fk_gm_member FOREIGN KEY (member_id) REFERENCES artists(id),

                               PRIMARY KEY (group_id, member_id)
);