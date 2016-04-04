
# --- !Ups

create table `scheduled_flight` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `flightId` INTEGER NOT NULL,
  `departureDate` DATE NOT NULL,
  `arrivalDate` DATE NOT NULL,
  `economySeats` INTEGER NOT NULL,
  `businessSeats` INTEGER NOT NULL,
  `isCancelled` tinyint(1) DEFAULT 0
  );


# --- !Downs

drop table `scheduled_flight`;
