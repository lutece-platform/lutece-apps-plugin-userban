DROP TABLE IF EXISTS userban_user;
CREATE TABLE IF NOT EXISTS userban_user (	
		guid varchar(255),
		state varchar(255),
		date timestamp,
		commentaire varchar(255),
	PRIMARY KEY  (guid)
) COMMENT='Contient les users';
