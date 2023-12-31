CREATE TABLE IF NOT EXISTS playerdata (
	uuid UUID NOT NULL,
	username VARCHAR(16) NOT NULL,
	first_played BIGINT NOT NULL,
	last_played BIGINT NOT NULL,
	rank SMALLINT NOT NULL DEFAULT 0,
	owner UUID,
	PRIMARY KEY (uuid)
);

CREATE INDEX IF NOT EXISTS ix_playerdata_owner ON "playerdata" ("owner");