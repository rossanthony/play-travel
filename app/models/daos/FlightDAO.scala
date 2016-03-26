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

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight): Future[Flight]
}
