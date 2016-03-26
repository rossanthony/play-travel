package models.services

import javax.inject.Inject
import models.Flight
import models.daos.FlightDAO
import scala.concurrent.Future

/**
  * Handles actions to flights.
  *
  * @param flightDAO The flight DAO implementation.
  */
class FlightServiceImpl @Inject() (flightDAO: FlightDAO) extends FlightService {

  /**
    * Retrieves a flight that matches the id.
    *
    * @param id The flight id.
    * @return The retrieved flight or None if no flight could be retrieved for the given flight id.
    */
  def retrieve(id: Int): Future[Option[Flight]] = flightDAO.find(id)


  def search(params: Map[String,Seq[String]]): Future[Seq[Flight]] = flightDAO.search(params)

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight) = flightDAO.save(flight)
}
