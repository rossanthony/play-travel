# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `FLIGHT` (`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`flightNumber` INTEGER NOT NULL,`airlineCode` VARCHAR(254) NOT NULL,`airlineName` VARCHAR(254) NOT NULL,`departureLocation` VARCHAR(254) NOT NULL,`departureDay` INTEGER NOT NULL,`departureTime` INTEGER NOT NULL,`arrivalLocation` VARCHAR(254) NOT NULL,`arrivalDay` INTEGER NOT NULL,`arrivalTime` INTEGER NOT NULL,`economyCost` INTEGER NOT NULL,`businessCost` INTEGER NOT NULL);
create table `USER` (`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`username` VARCHAR(254) NOT NULL,`password` VARCHAR(254) NOT NULL,`firstName` VARCHAR(254),`lastName` VARCHAR(254),`email` VARCHAR(254),`address` VARCHAR(254),`townCity` VARCHAR(254),`county` VARCHAR(254),`postcode` VARCHAR(254),`country` VARCHAR(254),`cardNumber` INTEGER,`expDate` INTEGER,`isAdmin` BOOLEAN NOT NULL);
create unique index `unique_username` on `USER` (`username`);

# --- !Downs

drop table `USER`;
drop table `FLIGHT`;

