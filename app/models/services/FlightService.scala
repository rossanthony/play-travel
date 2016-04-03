package models.services

import models._
import scala.concurrent.Future
import java.sql.Date

/**
  * Handles actions to flights.
  */
trait FlightService {

  /**
    * Save a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight): Future[Flight]

//  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[Flight]]
//  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[(Int, Int, String, String)]]
  def search(
              departureLocation: Option[String],
              arrivalLocation: Option[String],
              departureDate: Option[Date]
            ): Future[Seq[(Flight, ScheduledFlight, Airline, Airport, Airport)]] // (Flight, ScheduledFlight, Airline, Airport, Airport)

  /**
    * Delete a flight.
    *
    * @param id of the Flight to delete
    * @return A future to wait for the process to be completed.
    */
  def delete(id: Int): Future[Unit]
}
