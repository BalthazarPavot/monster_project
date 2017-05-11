SET FOREIGN_KEY_CHECKS = 0 ;

	DROP TABLE IF EXISTS `document` ;
	DROP TABLE IF EXISTS `document_text` ;
	DROP TABLE IF EXISTS `group`;
	DROP TABLE IF EXISTS `other`;
	DROP TABLE IF EXISTS `permission`;
	DROP TABLE IF EXISTS `permission_document_user`;
	DROP TABLE IF EXISTS `permission_document_group`;
	DROP TABLE IF EXISTS `permission_document_other`;
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

CREATE TABLE `other` (
	`id` VARCHAR(1	) NOT NULL PRIMARY KEY
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

CREATE TABLE `permission_document_group` (
	`id_permission` VARCHAR(90) NOT NULL,
	`id_document` VARCHAR(90) NOT NULL,
	`id_group` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_permission`) REFERENCES `permission`(`id`),
	FOREIGN KEY(`id_document`) REFERENCES `document`(`id`),
	FOREIGN KEY(`id_group`) REFERENCES `group`(`id`)
) ;

CREATE TABLE `permission_document_other` (
	`id_permission` VARCHAR(90) NOT NULL,
	`id_document` VARCHAR(90) NOT NULL,
	`id_other` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_permission`) REFERENCES `permission`(`id`),
	FOREIGN KEY(`id_document`) REFERENCES `document`(`id`),
	FOREIGN KEY(`id_other`) REFERENCES `other`(`id`)
) ;


CREATE TABLE `message_user` (
	`id_user` VARCHAR(90) NOT NULL,
	`id_message` VARCHAR(90) NOT NULL,
	FOREIGN KEY(`id_user`) REFERENCES `user`(`id`),
	FOREIGN KEY(`id_message`) REFERENCES `message`(`id`)
) ;

INSERT INTO `other` (`id`) VALUES ("1") ;

INSERT INTO `permission` (`id`, `r`, `w`) VALUES ("00", 0 , 0) ;
INSERT INTO `permission` (`id`, `r`, `w`) VALUES ("10", 1 , 0) ;
INSERT INTO `permission` (`id`, `r`, `w`) VALUES ("11", 1 , 1) ;
