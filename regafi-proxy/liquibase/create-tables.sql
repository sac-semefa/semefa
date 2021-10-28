-- User table
CREATE TABLE IF NOT EXISTS app_user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    encrypted BOOL NOT NULL,
    status VARCHAR(16) NOT NULL,
    PRIMARY KEY (id)
);

-- Role table
CREATE TABLE IF NOT EXISTS app_role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(16),
    user_id INT NOT NULL,
    UNIQUE(user_id, name),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES app_user (id)
);

-- Field Error table
CREATE TABLE IF NOT EXISTS field_error (
    id INT NOT NULL,
    name VARCHAR(256),
    PRIMARY KEY (id)
);

-- Field Error Rule table
CREATE TABLE IF NOT EXISTS field_error_rule (
    id INT NOT NULL,
    description VARCHAR(256),
    PRIMARY KEY (id)
);

-- Data Frame Status table
CREATE TABLE IF NOT EXISTS data_frame (
    id BIGINT NOT NULL AUTO_INCREMENT,
    message_id VARCHAR(64) NOT NULL,
    correlativeId VARCHAR(64),
    ack VARCHAR(64),
    `type` VARCHAR(16) NOT NULL,
    status VARCHAR(16) NOT NULL,
    process_date TIMESTAMP,
    attempts INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX message_id_unique_idx ON data_frame (message_id);