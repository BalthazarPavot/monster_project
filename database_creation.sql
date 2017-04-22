CREATE DATABASE IF NOT EXISTS monster_project ;

use monster_project ;

SELECT "Deleting old tables ..." AS 'Message : ' ;
/* If there addition/deletion of tables, run that query :
SELECT concat('DROP TABLE IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'monster_project';

Then replace lines between the setting of the foreign key constraint
by the output of that query */

SET FOREIGN_KEY_CHECKS = 0 ;

	DROP TABLE IF EXISTS document;
	DROP TABLE IF EXISTS grp;
	DROP TABLE IF EXISTS permission;
	DROP TABLE IF EXISTS permission_document_user;
	DROP TABLE IF EXISTS user;
	DROP TABLE IF EXISTS user_in_grp;

SET FOREIGN_KEY_CHECKS = 1 ;

SELECT "Creating tables ..." AS 'Message : ' ;
CREATE TABLE user (
	id VARCHAR(90) NOT NULL PRIMARY KEY,
	pseudo VARCHAR(20) UNIQUE,
	password VARCHAR(100)
) ;

CREATE TABLE grp (
	id VARCHAR(90) NOT NULL PRIMARY KEY,
	id_owner VARCHAR(90) NOT NULL,
	name VARCHAR(20) NOT NULL,
	FOREIGN KEY(id_owner) REFERENCES user(id)
) ;

CREATE TABLE document (
	id VARCHAR(90) NOT NULL PRIMARY KEY,
	id_owner VARCHAR(90) NOT NULL,
	name VARCHAR(20) NOT NULL,
	FOREIGN KEY(id_owner) REFERENCES user(id)
) ;

CREATE TABLE permission (
	id VARCHAR(90) NOT NULL PRIMARY KEY,
	r BOOLEAN NOT NULL,
	w BOOLEAN NOT NULL,
	x BOOLEAN NOT NULL
) ;

SELECT "Creating jointure tables ..." AS 'Message : ' ;
CREATE TABLE user_in_grp (
	id_user VARCHAR(90) NOT NULL,
	id_grp VARCHAR(90) NOT NULL,
	FOREIGN KEY(id_user) REFERENCES user(id),
	FOREIGN KEY(id_grp) REFERENCES grp(id)
) ;

CREATE TABLE permission_document_user (
	id_permission VARCHAR(90) NOT NULL,
	id_document VARCHAR(90) NOT NULL,
	id_user VARCHAR(90) NOT NULL,
	FOREIGN KEY(id_permission) REFERENCES permission(id),
	FOREIGN KEY(id_document) REFERENCES document(id),
	FOREIGN KEY(id_user) REFERENCES user(id)
) ;


SELECT "Filling tables ..." AS 'Message : ' ;
INSERT INTO user (id, pseudo, password) VALUES (1, "Miloune", "azertyuiop") ;
INSERT INTO user (id, pseudo, password) VALUES (2, "ToBal", "qsdfghjklm") ;
INSERT INTO user (id, pseudo, password) VALUES (3, "Moi", "wxcvbn");

INSERT INTO grp (id, id_owner, name) VALUES (1, 1, "MiTo") ;
INSERT INTO grp (id, id_owner, name) VALUES (2, 2, "ToMoi") ;

INSERT INTO user_in_grp (id_user, id_grp) VALUES (1, 1) ;
INSERT INTO user_in_grp (id_user, id_grp) VALUES (2, 1) ;
INSERT INTO user_in_grp (id_user, id_grp) VALUES (2, 2) ;
INSERT INTO user_in_grp (id_user, id_grp) VALUES (3, 2) ;

INSERT INTO document (id, id_owner, name) VALUES (1, 3, "MonDocument") ;

INSERT INTO permission (id, r, w, x) VALUES (1, 1, 1, 1) ;
INSERT INTO permission (id, r, w, x) VALUES (2, 1, 0, 0) ;

INSERT INTO permission_document_user (id_permission, id_document, id_user)
	VALUES (1, 1, 3) ;
INSERT INTO permission_document_user (id_permission, id_document, id_user)
	VALUES (2, 1, 1) ;
INSERT INTO permission_document_user (id_permission, id_document, id_user)
	VALUES (2, 1, 2) ;

SELECT user.pseudo, document.name, permission.r, permission.w, permission.x 
	FROM user, document, permission, permission_document_user
	WHERE user.id = permission_document_user.id_user 
	AND document.id = permission_document_user.id_document 
	AND permission.id = permission_document_user.id_permission ;


