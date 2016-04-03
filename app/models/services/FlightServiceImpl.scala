package models.services

import javax.inject.Inject
import models._
import models.daos.FlightDAO
import scala.concurrent.Future
import java.sql.Date

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

  /**
    * Delete a flight.
    *
    * @param id of the Flight to delete
    * @return A future to wait for the process to be completed.
    */
  def delete(id: Int): Future[Unit] = flightDAO.remove(id)

  /**
    *
    * @return
    */
  def getAllFlights: Future[Seq[Flight]] = flightDAO.getAllFlights

//  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[Flight]] =
//  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[(Int, Int, String, String)]] =
  def search(
              departureCity: Option[String],
              arrivalCity: Option[String],
              departureDate: Option[Date]
            ): Future[Seq[(Flight, ScheduledFlight, Airline, Airport, Airport)]] = {
    flightDAO.search(
      departureCity: Option[String],
      arrivalCity: Option[String],
      departureDate: Option[Date]
    )
  }

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight) = flightDAO.save(flight)
}
