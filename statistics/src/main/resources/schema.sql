drop table if exists statistics cascade;

CREATE TABLE IF NOT EXISTS statistics (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, app varchar(10), attributes json, created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP);




-- INSERT INTO statistics (app, attributes) values ( 'awm', '{"ip":"jnerjnfjen", "uri":"jsdnjnjenwfjwenjfn"}');
--
-- INSERT INTO statistics (app, attributes) values ( 'bbb', '{"ip":"jnerjnfjen", "uri":"jsdnjnjenwfjwenjfn"}');
-- INSERT INTO statistics (app, attributes) values ( 'bbb', '{"ip":"jnerjnfjen", "uri":"jsdnjnjenwfjwenjfn"}');