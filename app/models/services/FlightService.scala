package models.services

import models.{Flight, Airline}
import scala.concurrent.Future

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
  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[(Flight, Airline)]]

  /**
    * Delete a flight.
    *
    * @param id of the Flight to delete
    * @return A future to wait for the process to be completed.
    */
  def delete(id: Int): Future[Unit]
}
