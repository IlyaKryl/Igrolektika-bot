CREATE TABLE IF NOT EXISTS users
(
    id BIGINT PRIMARY KEY UNIQUE NOT NULL,
    chat_id BIGINT NOT NULL,
    first_name VARCHAR,
    phone VARCHAR,
    request VARCHAR,
    form_created_date TIMESTAMP,
    bot_state VARCHAR
);

CREATE TABLE IF NOT EXISTS questions
(
    id BIGINT PRIMARY KEY UNIQUE NOT NULL,
    chat_id BIGINT NOT NULL,
    question VARCHAR,
    message_id INTEGER,
    replied BOOLEAN DEFAULT FALSE,
    created TIMESTAMP NOT NULL,
    user_id BIGINT,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS users_id_seq
START WITH 1;

CREATE SEQUENCE IF NOT EXISTS questions_id_seq
START WITH 1;