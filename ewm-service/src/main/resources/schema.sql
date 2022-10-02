DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS participation_requests CASCADE;
DROP TABLE IF EXISTS event_user CASCADE;
DROP TABLE IF EXISTS event_compilation CASCADE;
DROP TYPE IF EXISTS enum_status cascade;

CREATE TYPE "enum_status" AS ENUM (
    'PENDING',
    'PUBLISHED',
    'CANCELED'
    );

CREATE TABLE "users"
(
    "id"      BIGSERIAL PRIMARY KEY,
    "email"   varchar(50) UNIQUE,
    "name"    varchar(50),
    "created" timestamp
);

CREATE TABLE "category"
(
    "id"          BIGSERIAL PRIMARY KEY,
    "name"        varchar UNIQUE,
    "description" varchar,
    "created"     timestamp
);

CREATE TABLE "events"
(
    "id"                 BIGSERIAL PRIMARY KEY,
    "title"              varchar(50),
    "annotation"         varchar(250),
    "description"        varchar(1000),
    "category_id"        bigint,
    "initiator_id"       bigint,
    "location_id"        bigint,
    "participant_limit"  int,
    "confirmed_requests" int,
    "lat"                float8,
    "lon"                float8,
    "paid"               boolean,
    "request_moderation" boolean,
    "status"             enum_status,
    "published_on"       timestamp,
    "event_date"         timestamp,
    "created"            timestamp
);

CREATE TABLE "compilations"
(
    "id"      BIGSERIAL PRIMARY KEY,
    "title"   varchar(100),
    "pinned"  boolean,
    "created" timestamp
);


CREATE TABLE "participation_requests"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "event_id"     bigint,
    "requestor_id" bigint,
    "status"       enum_status,
    "created"      timestamp
);

CREATE TABLE "event_user"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "event_id"     bigint,
    "requestor_id" bigint
);

CREATE TABLE "event_compilation"
(
    "id"             BIGSERIAL PRIMARY KEY,
    "event_id"       bigint,
    "compilation_id" bigint
);

ALTER TABLE "participation_requests"
    ADD FOREIGN KEY ("requestor_id") REFERENCES "users" ("id");

ALTER TABLE "participation_requests"
    ADD FOREIGN KEY ("event_id") REFERENCES "events" ("id");

ALTER TABLE "event_user"
    ADD FOREIGN KEY ("requestor_id") REFERENCES "users" ("id");

ALTER TABLE "event_user"
    ADD FOREIGN KEY ("event_id") REFERENCES "events" ("id");

ALTER TABLE "events"
    ADD FOREIGN KEY ("initiator_id") REFERENCES "users" ("id");

ALTER TABLE "category"
    ADD FOREIGN KEY ("id") REFERENCES "events" ("category_id");

ALTER TABLE "event_compilation"
    ADD FOREIGN KEY ("compilation_id") REFERENCES "compilations" ("id");

ALTER TABLE "event_compilation"
    ADD FOREIGN KEY ("event_id") REFERENCES "events" ("id");