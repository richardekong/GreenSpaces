--DROP SCHEMA
drop schema if exists greenspaces;

-- CREATE SCHEMA
create schema if not exists greenspaces;

set schema greenspaces;

-- DROP TABLES
drop table if exists authorities;
drop table if exists roles;
drop table if exists users;
drop table if exists redirect_urls;
drop table if exists token_settings;
drop table if exists grant_types;
drop table if exists scopes;
drop table if exists authentication_methods;
drop table if exists clients;
drop table if exists csrf_tokens;

-- CREATE TABLES

CREATE TABLE users
(
    id                      VARCHAR(36) NOT NULL PRIMARY KEY,
    username                VARCHAR(30) NOT NULL UNIQUE,
    `password`              TEXT        NOT NULL,
    account_non_expired     BOOLEAN DEFAULT 0,
    account_non_locked      BOOLEAN DEFAULT 0,
    credentials_non_expired BOOLEAN DEFAULT 0,
    account_enabled         BOOLEAN DEFAULT 0
);

CREATE TABLE roles
(
    id      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id VARCHAR(36)        NOT NULL,
    role    VARCHAR(20)        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE authorities
(
    id        INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    authority VARCHAR(20)        NOT NULL,
    role_id   INT                NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE clients
(
    id                    VARCHAR(36) NOT NULL PRIMARY KEY,
    client_id             VARCHAR(36) NOT NULL,
    `name`                VARCHAR(30) NOT NULL UNIQUE,
    secret                TEXT        NOT NULL
);

CREATE TABLE grant_types
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    grant_type VARCHAR(30)        NOT NULL,
    client_id  VARCHAR(36),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE scopes
(
    id        INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    scope     VARCHAR(30)        NOT NULL,
    client_id VARCHAR(36),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE authentication_methods
(
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    method VARCHAR(20) NOT NULL,
    client_id VARCHAR(36),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE redirect_uris
(
    id        INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    uri       TEXT               NOT NULL,
    client_id VARCHAR(36),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE token_settings
(
    id               INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    format           VARCHAR(30)        NOT NULL,
    access_token_ttl INT DEFAULT 30,
    client_id        VARCHAR(36),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE csrf_token
(
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    client_id  VARCHAR(36),
    token      VARCHAR(36),
    issued_at  TIME DEFAULT '00:00:00',
    expires_in INT  DEFAULT 0
);

