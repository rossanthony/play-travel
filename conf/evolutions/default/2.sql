
# --- !Ups

create table `flight` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `flightNumber` INTEGER NOT NULL,
  `airlineId` INTEGER NOT NULL,
  `departureLocation` INTEGER NOT NULL,
  `departureDay` INTEGER NOT NULL,
  `departureTime` INTEGER NOT NULL,
  `arrivalLocation` INTEGER NOT NULL,
  `arrivalDay` INTEGER NOT NULL,
  `arrivalTime` INTEGER NOT NULL,
  `economyCost` INTEGER NOT NULL,
  `businessCost` INTEGER NOT NULL
  );


# --- !Downs

drop table `flight`;
