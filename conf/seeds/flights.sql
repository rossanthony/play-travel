
-- BA123, British Airways flight 123, LHR to JFK, depart Weds 14:55, arrive Weds 09:05
INSERT INTO `flight` (`id`, `flightNumber`, `airlineId`, `departureAirportId`, `departureDay`, `departureTime`, `arrivalAirportId`, `arrivalDay`, `arrivalTime`, `economyCost`, `businessCost`)
VALUES
	(1, 123, 216, 2013, 3, 1455, 2540, 3, 905, 500, 1150);

-- x 4 weeks of the above flight BA123 flying every Wednesday
INSERT INTO `scheduled_flight` (`flightId`, `date`, `economySeats`, `businessSeats`)
VALUES
	(1, '2016-04-13', 90, 10),
	(1, '2016-04-20', 90, 10),
	(1, '2016-04-27', 90, 10),
	(1, '2016-05-04', 90, 10);


-- VS009, Virgin Atlantic flight 009, SFO to JFK, depart Mon 16:15, arrive Mon 18:50
INSERT INTO `flight` (`id`, `flightNumber`, `airlineId`, `departureAirportId`, `departureDay`, `departureTime`, `arrivalAirportId`, `arrivalDay`, `arrivalTime`, `economyCost`, `businessCost`)
VALUES
	(2, 9, 753, 3195, 1, 1615, 2540, 1, 905, 500, 1850);

-- x 4 weeks of the above flight VS009 flying every Monday
INSERT INTO `scheduled_flight` (`flightId`, `date`, `economySeats`, `businessSeats`)
VALUES
	(2, '2016-04-11', 90, 10),
	(2, '2016-04-18', 90, 10),
	(2, '2016-04-25', 90, 10),
	(2, '2016-05-02', 90, 10);


-- VS025, Virgin Atlantic flight 025, LHR to SFO, depart Mon 16:15, arrive Mon 18:50
INSERT INTO `flight` (`id`, `flightNumber`, `airlineId`, `departureAirportId`, `departureDay`, `departureTime`, `arrivalAirportId`, `arrivalDay`, `arrivalTime`, `economyCost`, `businessCost`)
VALUES
	(3, 25, 216, 2013, 3, 1145, 3195, 3, 620, 450, 810);

-- x 4 weeks of the above flight VS009 flying every Monday
INSERT INTO `scheduled_flight` (`flightId`, `date`, `economySeats`, `businessSeats`)
VALUES
	(3, '2016-04-13', 90, 10),
	(3, '2016-04-20', 90, 10),
	(3, '2016-04-27', 90, 10),
	(3, '2016-05-04', 90, 10);