package models.daos

import models.{Flight, ScheduledFlight, Airline, Airport}
import scala.concurrent.Future
import java.sql.Date

/**
  * Give access to the user object.
  */
trait FlightDAO {

  /**
    * Finds a flight by id.
    *
    * @param id The id of the flight.
    * @return The found flight or None if no flight found.
    */
  def find(id: Int): Future[Option[(Flight, Airline, Airport, Airport)]]


  def getAllFlights: Future[Seq[(Flight, Airline, Airport, Airport)]]


  def search(
              departureCity: Option[String],
              arrivalCity: Option[String],
              departureDate: Option[Date]
            ): Future[Seq[(Flight, ScheduledFlight, Airline, Airport, Airport)]]

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight): Future[Flight]

  /**
    * Remove a flight.
    *
    * @param id of the flight
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit]
}
