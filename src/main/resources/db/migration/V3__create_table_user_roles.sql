CREATE TABLE user_roles(
    id_user BIGINT REFERENCES users(id) NOT NULL,
    user_roles VARCHAR(50) DEFAULT 'ROLE_USER'
);