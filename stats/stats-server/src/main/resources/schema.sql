DROP TABLE IF EXISTS hits;

CREATE TABLE hits (
    id BIGSERIAL PRIMARY KEY,
    app VARCHAR NOT NULL,
    uri VARCHAR NOT NULL,
    ip VARCHAR NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS hits_timestamp_index ON hits (created_at);  -- btree(default), т.к. сравнения на > / <
CREATE INDEX IF NOT EXISTS hits_uri_index ON hits USING HASH (uri);  -- hash, т.к. только сравнения на ==
CREATE INDEX IF NOT EXISTS hits_app_index ON hits USING HASH (app);  -- hash, т.к. только сравнения на ==