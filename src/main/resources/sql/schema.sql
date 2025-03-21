CREATE SEQUENCE IF NOT EXISTS backups_id_seq;

CREATE TABLE backups (
	id INT NOT NULL DEFAULT nextval('backups_id_seq') PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    uncompressed_file_size_in_bytes BIGINT NOT NULL,
    file_extension VARCHAR(7) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO backups (file_path, uncompressed_file_size_in_bytes, file_extension, user_id)
    VALUES ('src/test/resources/storage/123-abc/test.zip', 22, 'txt', '123-abc');

CREATE SEQUENCE IF NOT EXISTS events_id_seq;
CREATE TABLE events (
	id INT NOT NULL DEFAULT nextval('events_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    start_date_time_in_utc TIMESTAMPTZ NOT NULL,
    end_date_time_in_utc TIMESTAMPTZ NOT NULL,
    created_date_time_in_utc TIMESTAMPTZ NOT NULL,
    modified_date_time_in_utc TIMESTAMPTZ NOT NULL
);

INSERT INTO events (
    name, user_id, start_date_time_in_utc, end_date_time_in_utc, created_date_time_in_utc, modified_date_time_in_utc)
VALUES (
    'Meeting One', '456-def', NOW() + INTERVAL '2 hours', NOW() + INTERVAL '3 hours', NOW(), NOW()
);

INSERT INTO events (
    name, user_id, start_date_time_in_utc, end_date_time_in_utc, created_date_time_in_utc, modified_date_time_in_utc)
VALUES (
    'Meeting Two', '456-def', NOW() + INTERVAL '4 hours', NOW() + INTERVAL '5 hours', NOW(), NOW()
);

CREATE SEQUENCE IF NOT EXISTS schedule_keys_id_seq;
CREATE TABLE schedule_keys (
	id INT NOT NULL DEFAULT nextval('schedule_keys_id_seq') PRIMARY KEY,
	user_id VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    created_date_time_in_utc TIMESTAMPTZ NOT NULL
);

INSERT INTO schedule_keys (user_id, token, is_active, created_date_time_in_utc) VALUES ('456-def', 'xyz', true, NOW());

CREATE SEQUENCE IF NOT EXISTS booking_request_statuses_id_seq;
CREATE TABLE booking_request_statuses (
    id INT NOT NULL DEFAULT nextval('booking_request_statuses_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    request_is_pending_approval BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO booking_request_statuses (name, request_is_pending_approval) VALUES ('pending approval', TRUE);
INSERT INTO booking_request_statuses (name, request_is_pending_approval) VALUES ('accepted', FALSE);
INSERT INTO booking_request_statuses (name, request_is_pending_approval) VALUES ('denied', FALSE);
INSERT INTO booking_request_statuses (name, request_is_pending_approval) VALUES ('canceled', FALSE);

CREATE SEQUENCE IF NOT EXISTS booking_requests_id_seq;
CREATE TABLE booking_requests (
	id INT NOT NULL DEFAULT nextval('booking_requests_id_seq') PRIMARY KEY,
	requestee_user_id VARCHAR(255) NOT NULL,
    requester_email_address VARCHAR (255) NOT NULL,
    status_id INT NOT NULL,
    start_date_time_in_utc TIMESTAMPTZ NOT NULL,
    end_date_time_in_utc TIMESTAMPTZ NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    created_date_time_in_utc TIMESTAMPTZ NOT NULL,
    modified_date_time_in_utc TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (status_id) REFERENCES booking_request_statuses (id)
);

INSERT INTO booking_requests (requestee_user_id, requester_email_address, status_id, start_date_time_in_utc,
    end_date_time_in_utc, event_name, created_date_time_in_utc, modified_date_time_in_utc)
    VALUES ('456-def', 'someone@mail.com',
        (SELECT id FROM booking_request_statuses WHERE booking_request_statuses.name = 'pending approval'),
        NOW(),
        NOW(),
        'Demo Meeting',
        NOW(), NOW());
