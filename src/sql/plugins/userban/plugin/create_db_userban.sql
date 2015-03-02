
--
-- Base de données: 'billetterie'
--
DROP TABLE IF EXISTS userban_user;
CREATE TABLE IF NOT EXISTS userban_user (
 id varchar(255),
 state varchar(255),
 date timestamp,
 commentaire varchar(255),
 motif varchar(255),
 PRIMARY KEY  (id)
) COMMENT='Contient les banissement d utilisateurs';