
INSERT INTO `user` (`userID`, `firstName`, `lastName`, `fullName`, `email`, `avatarURL`, `addressLine1`, `addressLine2`, `townCity`, `country`, `postcode`, `telephone`, `cardType`, `cardNumber`, `expDate`)
VALUES
	('083fd896-ef6c-4176-9e8f-3afca60079d0', 'Ross', 'Anthony', 'Ross Anthony', 'r@a.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `userlogininfo` (`userID`, `loginInfoId`)
VALUES
	('083fd896-ef6c-4176-9e8f-3afca60079d0', 1);

INSERT INTO `logininfo` (`id`, `providerID`, `providerKey`)
VALUES
	(1, 'credentials', 'r@a.com');

INSERT INTO `passwordinfo` (`hasher`, `password`, `salt`, `loginInfoId`)
VALUES
	('bcrypt', '$2a$10$lJxLQT5EoEO1GVL1vML52uKCXcFj262MdAnQGUEvTAYk4bpIhCRDS', NULL, 1);
