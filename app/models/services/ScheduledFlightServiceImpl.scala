package models.services

import javax.inject.Inject
import models.{ScheduledFlight, Airline, Airport}
import models.daos.ScheduledFlightDAO
import scala.concurrent.Future

/**
  * Handles actions to scheduledFlights.
  *
  * @param scheduledFlightDAO The scheduledFlight DAO implementation.
  */
class ScheduledFlightServiceImpl @Inject() (scheduledFlightDAO: ScheduledFlightDAO) extends ScheduledFlightService {

  /**
    * Retrieves a scheduledFlight that matches the id.
    *
    * @param id The scheduledFlight id.
    * @return The retrieved scheduledFlight or None if no scheduledFlight could be retrieved for the given scheduledFlight id.
    */
  def retrieve(id: Int): Future[Option[ScheduledFlight]] = scheduledFlightDAO.find(id)

  /**
    * Remove a scheduledFlight.
    *
    * @param id The id of the scheduledFlight to delete.
    * @return The saved scheduledFlight.
    */
  def delete(id: Int): Future[Unit] = scheduledFlightDAO.remove(id)

  def getAllScheduledFlights: Future[Seq[ScheduledFlight]] = scheduledFlightDAO.getAllScheduledFlights

  /**
    * Save a scheduledFlight.
    *
    * @param scheduledFlight The scheduledFlight to save.
    * @return The saved scheduledFlight.
    */
  def save(scheduledFlight: ScheduledFlight) = scheduledFlightDAO.save(scheduledFlight)
}
