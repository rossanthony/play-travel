package models.daos

import models.Flight
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.dbio.DBIOAction
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future


/**
  * Give access to the flight object using Slick
  */
class FlightDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends FlightDAO with DAOSlick {

  import driver.api._

  /**
    * Finds a flight by its id.
    *
    * @param id The ID of the flight.
    * @return The found flight or None if no flight found with given id.
    */
  def find(id: Int) = {
    val flightQuery = for {
      dbFlight <- slickFlights.filter(_.id === id)
    } yield dbFlight
    db.run(flightQuery.result.headOption).map { dbFlightOption =>
      dbFlightOption.map { flight =>
        Flight(
          flight.id,
          flight.flightNumber,
          flight.airlineCode,
          flight.airlineName,
          flight.departureLocation,
          flight.departureDay,
          flight.departureTime,
          flight.arrivalLocation,
          flight.arrivalDay,
          flight.arrivalTime,
          flight.economyCost,
          flight.businessCost
        )
      }
    }
  }

  /**
    * Search for flights.
    *
    * @param
    * @return The found flights or None if no flight(s) were found matching search criteria.
    */
  def search(params: Map[String,Seq[String]]) = {

    val paramsFlat = params.map { case (k,v) => k -> v.mkString }

    val departureLocation = paramsFlat("departureLocation").toString
    println(s"---$departureLocation---")

    val query = slickFlights.filter(_.departureLocation === departureLocation)
    db.run(query.result)
  }

  /**
    * Saves a flight.
    *
    * @param flight The flight to save.
    * @return The saved flight.
    */
  def save(flight: Flight) = {
    val dbFlight = Flight(
      flight.id,
      flight.flightNumber,
      flight.airlineCode,
      flight.airlineName,
      flight.departureLocation,
      flight.departureDay,
      flight.departureTime,
      flight.arrivalLocation,
      flight.arrivalDay,
      flight.arrivalTime,
      flight.economyCost,
      flight.businessCost
    )

    // combine database actions to be run sequentially
    val actions = (for {
      _ <- slickFlights.insertOrUpdate(dbFlight)
    } yield ()).transactionally
    // run actions and return user afterwards
    db.run(actions).map(_ => flight)
  }
}
