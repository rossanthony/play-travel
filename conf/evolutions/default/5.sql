# --- !Ups
create table `ticket` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `bookingId` INTEGER NOT NULL,
  `flightId` INTEGER NOT NULL,
  `ticketType` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
  );

# --- !Downs
drop table `ticket`;
