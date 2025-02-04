CREATE SEQUENCE IF NOT EXISTS backups_id_seq;

CREATE TABLE backups (
	id INT NOT NULL DEFAULT nextval('backups_id_seq') PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    uncompressed_file_size_in_bytes BIGINT NOT NULL,
    file_extension VARCHAR(7) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO backups (file_path, uncompressed_file_size_in_bytes, file_extension, user_id) VALUES ('src/test/resources/storage/123-abc/test.zip', 22, 'txt', '123-abc');
