drop table if exists statistics cascade;

CREATE TABLE IF NOT EXISTS statistics (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, app varchar(200), attributes json, created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP);


