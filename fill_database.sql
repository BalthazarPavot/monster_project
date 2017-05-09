use monster_project ;

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
