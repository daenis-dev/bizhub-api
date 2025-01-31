CREATE SEQUENCE IF NOT EXISTS backups_id_seq;

CREATE TABLE backups (
	id INT NOT NULL DEFAULT nextval('backups_id_seq') PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    is_compressed BOOLEAN NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO artifacts (name, file_name, hash, user_id) VALUES ('File 1', 'C:/Windows/System32/svchost.exe', true, '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('File 2', 'C:/Windows/System32/lsass.exe', true, '123-abc');
INSERT INTO artifacts (name, file_path, hash, user_id) VALUES ('File 3', 'C:/Windows/System32/netsh.exe', false, '123-abc');
