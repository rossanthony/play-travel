package models.daos

import models.Flight
import net.liftweb.util.True
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.dbio.DBIOAction
import slick.lifted.Query
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

import scala.reflect.internal.util.TableDef.Column

//import slick.model.Column
import scala.concurrent.Future





//import slick.lifted.CanBeQueryCondition
//// optionally filter on a column with a supplied predicate
//case class MaybeFilter[X, Y](val query: slick.lifted.Query[X, Y, Seq]) {
//  def filter[T,R:CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
//    data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
//  }
//}


//case class OptionFilter[ X, Y ]( query: Query[ X, Y, Seq ] ) {
//  def filteredBy[ T ]( op: Option[ T ] )( f: ( X, T ) => Column[ Option[ Boolean ] ] ): Query[ X, Y, Seq ] = {
//    op map { o => query.filter( f( _, o ) ) } getOrElse { query }
//  }
//
//  def foundBy[ T ]( op: Option[ T ] )( f: ( X, T ) => Column[ Option[ Boolean ] ] ): Query[ X, Y, Seq ] = {
//    op map { o => query.filter( f( _, o ) ) } getOrElse { query.take( 0 ) }
//  }
//}

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



//  def userNameByIDRange(min: Rep[Int], max: Rep[Int]) =
//    for {
//      u <- users if u.id >= min && u.id < max
//    } yield u.first
//
//  val userNameByIDRangeCompiled = Compiled(userNameByIDRange _)
//
//  // The query will be compiled only once:
//  val namesAction1 = userNameByIDRangeCompiled(2, 5).result
//  val namesAction2 = userNameByIDRangeCompiled(1, 3).result
//
//
//  def flightByDestAndOrigin(from: Option[String], to: Option[String]) =
//    data.filter { d =>
//      (Case.If(min.isDefined) Then d.id >= min Else True) &&
//        (Case.If(max.isDefined) Then d.id <= max Else True)
//    }

  /**
    * Search for flights.
    *
    * @param
    * @return The found flights or None if no flight(s) were found matching search criteria.
    */
  def search(departureLocation: Option[String], arrivalLocation: Option[String]) = {

    println(departureLocation)
    println(arrivalLocation)

    val query = slickFlights.filter(f =>
      f.departureLocation === departureLocation &&
      f.arrivalLocation === arrivalLocation
    )

    val action = query.result
    val sql = action.statements.head
    println(sql)

    db.run(action)
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

  /**
    * Remove a flight.
    *
    * @param id of the flight
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit] = db.run(queryById(id).delete).map(_ => ())
    //db.run(slickFlights.filter(_.id === id).delete).map(_ => ())
}
