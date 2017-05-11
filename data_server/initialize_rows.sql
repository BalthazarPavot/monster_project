SET FOREIGN_KEY_CHECKS = 0 ;

	DELETE FROM `document` ;
	DELETE FROM `document_text` ;
	DELETE FROM `group`;
	DELETE FROM `other`;
	DELETE FROM `permission`;
	DELETE FROM `permission_document_user`;
	DELETE FROM `permission_document_group`;
	DELETE FROM `permission_document_other`;
	DELETE FROM `user`;
	DELETE FROM `user_in_group`;
	DELETE FROM `message`;
	DELETE FROM `message_user`;

SET FOREIGN_KEY_CHECKS = 1 ;

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

INSERT INTO `document_text` (`id`, `id_parent`, `content`) VALUES (1, 1, "du texte") ;

INSERT INTO `permission` (`id`, `r`, `w`) VALUES (1, 1, 1) ;
INSERT INTO `permission` (`id`, `r`, `w`) VALUES (2, 1, 0) ;

INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (1, 1, 3) ;
INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (2, 1, 1) ;
INSERT INTO `permission_document_user` (`id_permission`, `id_document`, `id_user`)
	VALUES (2, 1, 2) ;
