package models.services

import models.ScheduledFlight
import scala.concurrent.Future

/**
  * Handles actions to scheduledFlights.
  */
trait ScheduledFlightService {

  /**
    * Save a scheduledFlight.
    *
    * @param scheduledFlight The scheduledFlight to save.
    * @return The saved scheduledFlight.
    */
  def save(scheduledFlight: ScheduledFlight): Future[ScheduledFlight]

  /**
    * Delete a scheduledFlight.
    *
    * @param id of the scheduledFlight to delete.
    * @return
    */
  def delete(id: Int): Future[Unit]
}
