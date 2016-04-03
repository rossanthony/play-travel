
-- All flights, with info about departure/arrival airports and airline
SELECT
  `flight`.*,
  `scheduled_flight`.*,
  `arrival_airport`.name as `arrivalAirportName`, 
  `arrival_airport`.code as `arrivalAirportCode`, 
  `arrival_airport`.cityCode as `arrivalAirportCityCode`, 
  `arrival_airport`.cityName as `arrivalAirportCityName`,
  `departure_airport`.name as `departureAirportName`, 
  `departure_airport`.code as `departureAirportCode`, 
  `departure_airport`.cityCode as `departureAirportCityCode`, 
  `departure_airport`.cityName as `departureAirportCityName`
  	
FROM `scheduled_flight`
JOIN `flight` on `flight`.id = `scheduled_flight`.flightId
JOIN `airport` as `arrival_airport` on `flight`.arrivalAirportId = `arrival_airport`.id 
JOIN `airport` as `departure_airport` on `flight`.departureAirportId = `departure_airport`.id



SELECT
  `flight`.*,
  `scheduled_flight`.*,
  `arrival_airport`.name as `arrivalAirportName`,
  `arrival_airport`.code as `arrivalAirportCode`,
  `arrival_airport`.cityCode as `arrivalAirportCityCode`,
  `arrival_airport`.cityName as `arrivalAirportCityName`,
  `departure_airport`.name as `departureAirportName`,
  `departure_airport`.code as `departureAirportCode`,
  `departure_airport`.cityCode as `departureAirportCityCode`,
  `departure_airport`.cityName as `departureAirportCityName`

FROM `scheduled_flight`,
  `flight`,
  `airport` as `arrival_airport`,
  `airport` as `departure_airport`

WHERE
  `flight`.id = `scheduled_flight`.flightId AND
  `flight`.arrivalAirportId = `arrival_airport`.id AND
  `flight`.departureAirportId = `departure_airport`.id