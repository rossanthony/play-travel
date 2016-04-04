package models.daos

import models.Flight
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.{BaseTypedType, TypedType}
import scala.concurrent.Future
import slick.lifted.LiteralColumn
import java.sql.Date

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
      fl <- slickFlights.filter(_.id === id)
      al <- slickAirlines if fl.airlineId === al.id
      da <- slickAirports if fl.departureAirportId === da.id // da =  departure airport
      aa <- slickAirports if fl.arrivalAirportId === aa.id // aa =  arrival airport
    } yield (fl, al, da, aa)

    val actions = flightQuery.result
    val sql = actions.statements.head
    Logger.debug(s"find flight sql: $sql")

    db.run(flightQuery.result.headOption)
  }

  /**
    * Search for flights.
    *
    * @param
    * @return The found flights or None if no flight(s) were found matching search criteria.
    */
  def search(
              departureCity: Option[String],
              arrivalCity: Option[String],
              departureDate: Option[Date]
            ) = {

    val monadicJoin = for {
      sf <- slickScheduledFlights.filter(a =>
              departureDate.map(d => a.departureDate === d).getOrElse(slick.lifted.LiteralColumn(true))
            )
      fl <- slickFlights if sf.flightId === fl.id
      al <- slickAirlines if fl.airlineId === al.id
      //da <- slickAirports if fl.departureAirportId === da.id // da =  departure airport
      da <- slickAirports.filter(a =>
              fl.departureAirportId === a.id &&
              departureCity.map(c => a.cityCode === c).getOrElse(slick.lifted.LiteralColumn(true))
            )
      //aa <- slickAirports if fl.arrivalAirportId === aa.id // aa = arrival airport
      aa <- slickAirports.filter(a =>
              fl.arrivalAirportId === a.id &&
              arrivalCity.map(c => a.cityCode === c).getOrElse(slick.lifted.LiteralColumn(true))
            )
    } yield (fl, sf, al, da, aa)

    /*
    Search filters:
      - departure location, cityName/Code of apd (

     */
//    val flights = for {
//      flight <- slickFlights.filter(f =>
//        departureAirportId.map(d => f.departureAirportId === d).getOrElse(slick.lifted.LiteralColumn(true)) &&
//        arrivalAirportId.map(a => f.arrivalAirportId === a).getOrElse(slick.lifted.LiteralColumn(true))
//      )
//    } yield flight

//    val actions = monadicJoin.result
//    val sql = actions.statements.head
    //println(sql)

    db.run(monadicJoin.result)
  }

//  def getAllFlights = db.run(slickFlights.result)
  def getAllFlights = {
    val flightsQuery = for {
      fl <- slickFlights
      al <- slickAirlines if fl.airlineId === al.id
      da <- slickAirports if fl.departureAirportId === da.id // da =  departure airport
      aa <- slickAirports if fl.arrivalAirportId === aa.id // aa =  arrival airport
    } yield (fl, al, da, aa)

    val actions = flightsQuery.result
    val sql = actions.statements.head
    Logger.debug(s"find all flights sql: $sql")

    db.run(flightsQuery.result)
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
      flight.airlineId,
      flight.departureAirportId,
      flight.departureDay,
      flight.departureTime,
      flight.arrivalAirportId,
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
