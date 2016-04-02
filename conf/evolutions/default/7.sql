
# --- !Ups

create table `scheduled_flight` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `flightId` INTEGER NOT NULL,
  `date` DATE NOT NULL,
  `economySeats` INTEGER NOT NULL,
  `businessSeats` INTEGER NOT NULL
  );


# --- !Downs

drop table `scheduled_flight`;
