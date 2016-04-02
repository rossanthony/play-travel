package models.services

import javax.inject.Inject
import models.{Flight, Airline, Airport}
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
  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]): Future[Seq[(Flight, Airline)]] =
    flightDAO.search(departureLocation: Option[Int], arrivalLocation: Option[Int])

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight) = flightDAO.save(flight)
}
