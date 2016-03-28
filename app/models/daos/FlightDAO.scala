package models.daos

import models.Flight
import scala.concurrent.Future

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
  def find(id: Int): Future[Option[Flight]]


  def getAllFlights: Future[Seq[Flight]]


  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[Flight]]

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
