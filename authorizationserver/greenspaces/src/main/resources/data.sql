INSERT INTO USERS (id, username, password, account_non_expired, account_non_locked, credentials_non_expired,
                   account_enabled)
VALUES ('6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2',
        'admin@greenspaces.com',
        '$2y$10$RhQBqxVOBF2JBzSmfBWIOe5H4o.4aQ57cUh5oFOUND88Zsqv6odXG',
        true, true, true, true);

INSERT INTO ROLES (id, user_id, role)
VALUES (1,
        '6f13b449-71b5-4f5f-9f9e-6e5c3a23d5a2',
        'ADMIN');

INSERT INTO AUTHORITIES (id, authority, role_id)
VALUES (1,
        'write',
        1);

INSERT INTO CLIENTS (id, client_id, `name`, secret)
VALUES ('76e09d7a-1095-4b47-80c5-5f8bcba70f1d',
        'cce53fc6-5b4d-4f62-a9ff-27b1b837a30b',
        'Client01',
        '$2y$10$hAVbqcuf1cShpDmLUAFXrePVGvD6uJWF8qbbKt0MqZW2GolpkSm2y'
);

INSERT INTO GRANT_TYPES (id, grant_type, client_id)
VALUES (1,
        'authorization_code',
        '76e09d7a-1095-4b47-80c5-5f8bcba70f1d');

INSERT INTO SCOPES (id, scope, client_id)
VALUES (1,
        'read',
        '76e09d7a-1095-4b47-80c5-5f8bcba70f1d');

INSERT INTO AUTHENTICATION_METHODS (id, method, client_id)
        VALUES(
        1, 'client_secret_basic', '76e09d7a-1095-4b47-80c5-5f8bcba70f1d'
);

INSERT INTO AUTHENTICATION_METHODS (id, method, client_id)
        VALUES(
        2, 'client_secret_jwt', '76e09d7a-1095-4b47-80c5-5f8bcba70f1d'
);

INSERT INTO REDIRECT_URIS (id, uri, client_id)
VALUES (1,
        'https://localhost:1000',
        '76e09d7a-1095-4b47-80c5-5f8bcba70f1d');

INSERT INTO TOKEN_SETTINGS (id, format, access_token_ttl, client_id)
VALUES (1,
        'self-contained',
        15,
        '76e09d7a-1095-4b47-80c5-5f8bcba70f1d');

INSERT INTO CSRF_TOKEN (id, client_id, token) VALUES (
    'ea9ac3a7-2cb2-45c6-80a7-f3ab9fe15b44',
    '76e09d7a-1095-4b47-80c5-5f8bcba70f1d',
    'a2935e8c-63db-4b67-b3e5-5d94cf05c51f'
);

