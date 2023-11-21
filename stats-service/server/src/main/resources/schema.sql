DROP TABLE IF EXISTS hits CASCADE;

create table if not exists hits
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app       VARCHAR(30)  NOT NULL,
    uri       VARCHAR(300) NOT NULL,
    ip        VARCHAR(50)  NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL
);