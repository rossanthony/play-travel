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

  def search(params: Map[String,Seq[String]]): Future[Seq[Flight]]
}
