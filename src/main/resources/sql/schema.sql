CREATE SEQUENCE IF NOT EXISTS artifacts_id_seq;

CREATE TABLE artifacts (
    id INT NOT NULL DEFAULT nextval('artifacts_id_seq') PRIMARY KEY,
    name VARCHAR(255),
    file_path VARCHAR(255),
    hash BYTEA,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('File 1', 'C:/some/file1.txt', decode('1234567890abcde1', 'hex'), '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('File 2', 'C:/some/file2.txt', decode('1234567890abcde2', 'hex'), '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('File 3', 'C:/some/file3.txt', decode('1234567890abcde3', 'hex'), '123-abc');
