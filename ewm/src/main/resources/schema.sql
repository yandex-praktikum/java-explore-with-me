DROP TYPE if exists status cascade;
DROP TYPE if exists state cascade;
drop table if exists users cascade;
drop table if exists categories cascade;
drop table if exists events cascade;
drop table if exists requests cascade;
drop table if exists compilation cascade;
drop table if exists compilation_events cascade;
drop table if exists comments cascade;


CREATE TABLE IF NOT EXISTS users (user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, name varchar(100) NOT NULL, email varchar(35) NOT NULL, CONSTRAINT email_unique UNIQUE (email));
CREATE TABLE IF NOT EXISTS categories (category_id int4 GENERATED ALWAYS AS IDENTITY PRIMARY KEY, name varchar (30), CONSTRAINT name_unique UNIQUE (name));
CREATE TABLE IF NOT EXISTS events (event_id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, title varchar (120), annotation varchar (2000), description varchar (7000), initiator_id bigint, category_id int, created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP, published_at TIMESTAMP WITHOUT TIME ZONE, event_date TIMESTAMP WITHOUT TIME ZONE, paid boolean, request_moderation boolean, state varchar, participant_limit int, lat varchar, lon varchar, views int, CONSTRAINT initiator_fk FOREIGN KEY (initiator_id) REFERENCES users(user_id) ON DELETE CASCADE, CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES categories(category_id));
CREATE TABLE IF NOT EXISTS requests (request_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, requestor_id BIGINT, event_id BIGINT, created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP, status varchar, CONSTRAINT fk_requestor FOREIGN KEY (requestor_id) REFERENCES users(user_id) ON DELETE CASCADE, CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS compilation (compilation_id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, title varchar (120), pinned boolean);
CREATE TABLE IF NOT EXISTS compilation_events (compilation_id bigint, event_id bigint, PRIMARY KEY(compilation_id, event_id), CONSTRAINT compilation_fk FOREIGN KEY (compilation_id) REFERENCES compilation(compilation_id) ON DELETE CASCADE, CONSTRAINT event_fk FOREIGN KEY (event_id)  REFERENCES events(event_id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS comments (comment_id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, event_id bigint, text varchar (1000), created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,  moderation boolean, published_at TIMESTAMP WITHOUT TIME ZONE, author bigint, CONSTRAINT author_fk FOREIGN KEY (author) REFERENCES users(user_id) ON DELETE CASCADE, CONSTRAINT event_fk FOREIGN KEY (event_id)  REFERENCES events(event_id) ON DELETE CASCADE);
