CREATE DATABASE IF NOT EXISTS monster_project ;

use monster_project ;

SELECT "Deleting old tables ..." AS 'Message : ' ;
/* If there addition/deletion of tables, run that query :
SELECT concat('DROP TABLE IF EXISTS ', table_`name`, ';')
FROM information_schema.tables
WHERE table_schema = 'monster_project';

Then replace lines between the setting of the foreign key constraint
by the output of that query 

*/

SET FOREIGN_KEY_CHECKS = 0 ;

	DROP TABLE IF EXISTS `document` ;
	DROP TABLE IF EXISTS `document_text` ;
	DROP TABLE IF EXISTS `group`;
	DROP TABLE IF EXISTS `permission`;
	DROP TABLE IF EXISTS `permission_document_user`;
	DROP TABLE IF EXISTS `user`;
	DROP TABLE IF EXISTS `user_in_group`;
	DROP TABLE IF EXISTS `message`;
	DROP TABLE IF EXISTS `message_user`;

SET FOREIGN_KEY_CHECKS = 1 ;

SELECT "Creating tables ..." AS 'Message : ' ;
CREATE TABLE `user` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`pseudo` VARCHAR(32) NOT NULL UNIQUE,
	`password` VARCHAR(100) NOT NULL,
	`email` VARCHAR(32) NOT NULL UNIQUE
) ;

CREATE TABLE `group` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`id_owner` VARCHAR(90) NOT NULL,
	`name` VARCHAR(32) NOT NULL,
	FOREIGN KEY(`id_owner`) REFERENCES `user`(`id`)
) ;

CREATE TABLE `document` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`id_owner` VARCHAR(90) NOT NULL,
	`name` VARCHAR(32) NOT NULL,
	FOREIGN KEY(`id_owner`) REFERENCES `user`(`id`)
) ;

CREATE TABLE `document_text` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`id_parent` VARCHAR(90) NOT NULL,
	`content` TEXT,
	FOREIGN KEY(`id_parent`) REFERENCES `document`(`id`)
) ;

CREATE TABLE `permission` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`r` BOOLEAN NOT NULL,
	`w` BOOLEAN NOT NULL
) ;

CREATE TABLE `message` (
	`id` VARCHAR(90) NOT NULL PRIMARY KEY,
	`id_user` VARCHAR(90) NOT NULL,
	`content` VARCHAR(140) NOT NULL,
	FOREIGN KEY(`id_user`) REFERENCES `user`(`id`)
) ;

SELECT "Creating jointure tables ..." AS 'Message : ' ;
CREATE TABLE `user_in_group` (
	`id_user` VARCHAR(90) NOT NULL,
	`id_group` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_user`) REFERENCES `user`(`id`),
	FOREIGN KEY(`id_group`) REFERENCES `group`(`id`)
) ;

CREATE TABLE `permission_document_user` (
	`id_permission` VARCHAR(90) NOT NULL,
	`id_document` VARCHAR(90) NOT NULL,
	`id_user` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_permission`) REFERENCES `permission`(`id`),
	FOREIGN KEY(`id_document`) REFERENCES `document`(`id`),
	FOREIGN KEY(`id_user`) REFERENCES `user`(`id`)
) ;

CREATE TABLE `message_user` (
	`id_user` VARCHAR(90) NOT NULL,
	`id_message` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_user`) REFERENCES `user`(`id`),
	FOREIGN KEY(`id_message`) REFERENCES `message`(`id`)
) ;


SELECT "Filling tables ..." AS 'Message : ' ;
INSERT INTO `user` (`id`, `pseudo`, `password`, `email`) VALUES (1, "Miloune", "azertyuiop", "orefefikj@ij") ;
INSERT INTO `user` (`id`, `pseudo`, `password`, `email`) VALUES (2, "ToBal", "qsdfghjklm", "oikefj@ij") ;
INSERT INTO `user` (`id`, `pseudo`, `password`, `email`) VALUES (3, "Moi", "wxcvbn", "oizdzdkj@ij");

INSERT INTO `group` (`id`, `id_owner`, `name`) VALUES (1, 1, "MiTo") ;
INSERT INTO `group` (`id`, `id_owner`, `name`) VALUES (2, 2, "ToMoi") ;

INSERT INTO `user_in_group` (`id_user`, `id_group`) VALUES (1, 1) ;
INSERT INTO `user_in_group` (`id_user`, `id_group`) VALUES (2, 1) ;
INSERT INTO `user_in_group` (`id_user`, `id_group`) VALUES (2, 2) ;
INSERT INTO `user_in_group` (`id_user`, `id_group`) VALUES (3, 2) ;

INSERT INTO `document` (`id`, `id_owner`, `name`) VALUES (1, 3, "Mondocument") ;

INSERT INTO `document_text` (`id`, `id_parent`, `content`) VALUES (1, 1, 
"Badass") ;

INSERT INTO `permission` (`id`, `r`, `w`) VALUES (1, 1, 1) ;
INSERT INTO `permission` (`id`, `r`, `w`) VALUES (2, 1, 0) ;

INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (1, 1, 3) ;
INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (2, 1, 1) ;
INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (2, 1, 2) ;

/*SELECT `user`.`pseudo`, `document`.`name`, `permission`.`r`, `permission`.`w`, `permission`.`x`, `content`
	FROM `user`, `document`, `permission`, `permission_document_user`, `document_text` 
	WHERE `user`.`id` = `permission_document_user`.`id_user` 
	AND `document`.`id` = `permission_document_user`.`id_document` 
	AND `permission`.`id` = `permission_document_user`.`id_permission` 
	AND `document`.`id` = `document_text`.`id_parent` ;*/
