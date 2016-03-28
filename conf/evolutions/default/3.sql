# --- !Ups
CREATE TABLE `airport` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `cityCode` varchar(50) DEFAULT NULL,
  `cityName` varchar(200) DEFAULT NULL,
  `countryName` varchar(200) DEFAULT NULL,
  `countryCode` varchar(200) DEFAULT NULL,
  `timezone` varchar(8) DEFAULT NULL,
  `lat` varchar(32) DEFAULT NULL,
  `lon` varchar(32) DEFAULT NULL,
  `numAirports` int(11) DEFAULT NULL,
  `city` tinyint(1)
);

# --- !Downs
drop table `airport`;
