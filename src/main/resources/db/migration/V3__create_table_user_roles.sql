CREATE TABLE user_roles(
    user_id BIGINT REFERENCES users(id) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
    PRIMARY KEY (user_id, role)
);