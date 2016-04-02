package models.daos

import models.Flight
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.{BaseTypedType, TypedType}
import scala.concurrent.Future
import slick.lifted.LiteralColumn

/**
  * Give access to the flight object using Slick
  */
class FlightDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends FlightDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickFlights.filter(_.id === id))

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
          flight.airlineId,
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
  def search(departureLocation: Option[Int], arrivalLocation: Option[Int]) = {

    val monadicInnerJoin = for {
      fl <- slickFlights.filter(f =>
                departureLocation.map(d => f.departureLocation === d).getOrElse(slick.lifted.LiteralColumn(true)) &&
                arrivalLocation.map(a => f.arrivalLocation === a).getOrElse(slick.lifted.LiteralColumn(true))
              )
      al <- slickAirlines if fl.airlineId === al.id
      //apd <- slickAirports if fl.departureLocation === apd.id // apd = airport departure
      //apa <- slickAirports if fl.airlineId === apa.id // apd = airport arrival
    } yield (fl, al)

//    val flights = for {
//      flight <- monadicInnerJoin.filter(f =>
//        departureLocation.map(d => f.f.departureLocation === d).getOrElse(slick.lifted.LiteralColumn(true)) &&
//        arrivalLocation.map(a => f.arrivalLocation === a).getOrElse(slick.lifted.LiteralColumn(true))
//      )
//    } yield flight



    val actions = monadicInnerJoin.result
    val sql = actions.statements.head
    println(sql)

    db.run(actions.transactionally)
  }

  def getAllFlights = db.run(slickFlights.result)

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
      flight.airlineId,
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

  /**
    * Remove a flight.
    *
    * @param id of the flight
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit] = db.run(queryById(id).delete).map(_ => ())
}
