# --- !Ups
CREATE TABLE `airline` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `iata` char(2) DEFAULT NULL,
  `icao` char(3) DEFAULT NULL,
  `airlineName` varchar(200) DEFAULT NULL,
  `countryName` varchar(200) DEFAULT NULL
);

# --- !Downs
drop table `airline`;
