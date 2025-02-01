CREATE SEQUENCE IF NOT EXISTS backups_id_seq;

CREATE TABLE backups (
	id INT NOT NULL DEFAULT nextval('backups_id_seq') PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    file_extension VARCHAR(7) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO artifacts (name, file_name, hash, user_id) VALUES ('src/main/resources/svchost.zip', '.exe', '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('src/main/resources/lsass.zip', '.exe', '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('src/main/resources/netsh.zip', '.exe', '123-abc');
