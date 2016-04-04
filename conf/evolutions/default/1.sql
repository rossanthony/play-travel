# --- !Ups

create table `user` (
  `userID` VARCHAR(254) NOT NULL,
  `firstName` VARCHAR(254),
  `lastName` VARCHAR(254),
  `fullName` VARCHAR(254),
  `email` VARCHAR(254),
  `avatarURL` VARCHAR(254),
  `addressLine1` VARCHAR(254) DEFAULT NULL,
  `addressLine2` VARCHAR(254) DEFAULT NULL,
  `townCity` VARCHAR(254) DEFAULT NULL,
  `country` VARCHAR(254) DEFAULT NULL,
  `postcode` VARCHAR(254) DEFAULT NULL,
  `telephone` VARCHAR(254) DEFAULT NULL,
  `cardType` VARCHAR(254) DEFAULT NULL,
  `cardNumber` INTEGER DEFAULT NULL,
  `expDate` INTEGER DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`userID`)
);

create table `logininfo` (`id` BIGINT NOT NULL AUTO_INCREMENT,`providerID` VARCHAR(254) NOT NULL,`providerKey` VARCHAR(254) NOT NULL, PRIMARY KEY (`id`));
create table `userlogininfo` (`userID` VARCHAR(254) NOT NULL,`loginInfoId` BIGINT NOT NULL);
create table `passwordinfo` (`hasher` VARCHAR(254) NOT NULL,`password` VARCHAR(254) NOT NULL,`salt` VARCHAR(254),`loginInfoId` BIGINT NOT NULL);
create table `oauth1info` (`id` BIGINT NOT NULL AUTO_INCREMENT,`token` VARCHAR(254) NOT NULL,`secret` VARCHAR(254) NOT NULL,`loginInfoId` BIGINT NOT NULL, PRIMARY KEY (`id`));
create table `oauth2info` (`id` BIGINT NOT NULL AUTO_INCREMENT,`accesstoken` VARCHAR(254) NOT NULL,`tokentype` VARCHAR(254),`expiresin` INTEGER,`refreshtoken` VARCHAR(254),`logininfoid` BIGINT NOT NULL, PRIMARY KEY (`id`));
create table `openidinfo` (`id` VARCHAR(254) NOT NULL,`logininfoid` BIGINT NOT NULL, PRIMARY KEY (`id`));
create table `openidattributes` (`id` VARCHAR(254) NOT NULL,`key` VARCHAR(254) NOT NULL,`value` VARCHAR(254) NOT NULL);


# --- !Downs

drop table `openidattributes`;
drop table `openidinfo`;
drop table `oauth2info`;
drop table `oauth1info`;
drop table `passwordinfo`;
drop table `userlogininfo`;
drop table `logininfo`;
drop table `user`;
