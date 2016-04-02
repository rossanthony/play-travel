package models.daos

import models.ScheduledFlight
import scala.concurrent.Future

/**
  * Give access to the user object.
  */
trait ScheduledFlightDAO {

  /**
    * Finds a scheduled flight by id.
    *
    * @param id The id of the scheduled flight.
    * @return The found scheduled flight or None if no scheduled flight found.
    */
  def find(id: Int): Future[Option[ScheduledFlight]]
  
  def getAllScheduledFlights: Future[Seq[ScheduledFlight]]

  /**
    * Saves a scheduled flight.
    *
    * @param scheduled flight The scheduled flight to save.
    * @return The saved scheduled flight.
    */
  def save(scheduledFlight: ScheduledFlight): Future[ScheduledFlight]

  /**
    * Remove a scheduled flight.
    *
    * @param id of the scheduled flight
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit]
}
