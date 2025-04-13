DELETE FROM endpoint_hit;
DELETE FROM view_stats;

ALTER TABLE endpoint_hit ALTER COLUMN id RESTART WITH 1;