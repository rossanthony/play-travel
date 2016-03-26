
# --- !Ups

create table `flight` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `flightNumber` INTEGER NOT NULL,
  `airlineCode` VARCHAR(254) NOT NULL,
  `airlineName` VARCHAR(254) NOT NULL,
  `departureLocation` VARCHAR(254) NOT NULL,
  `departureDay` INTEGER NOT NULL,
  `departureTime` INTEGER NOT NULL,
  `arrivalLocation` VARCHAR(254) NOT NULL,
  `arrivalDay` INTEGER NOT NULL,
  `arrivalTime` INTEGER NOT NULL,
  `economyCost` INTEGER NOT NULL,
  `businessCost` INTEGER NOT NULL
  );


# --- !Downs

drop table `flight`;