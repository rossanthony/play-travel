package models.services

import models.Flight
import scala.concurrent.Future

/**
  * Handles actions to flights.
  */
trait FlightService {

  /**
    * Saves a user.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight): Future[Flight]

  def search(departureLocation: Option[String], arrivalLocation: Option[String]): Future[Seq[Flight]]

  def delete(id: Int): Future[Unit]
}
