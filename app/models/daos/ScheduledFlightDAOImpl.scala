package models.daos

import models.ScheduledFlight
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

/**
  * Give access to the scheduledFlight object using Slick
  */
class ScheduledFlightDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends ScheduledFlightDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickScheduledFlights.filter(_.id === id))

  /**
    * Finds a scheduledFlight by its id.
    *
    * @param id The ID of the scheduledFlight.
    * @return The found scheduledFlight or None if no scheduledFlight found with given id.
    */
  def find(id: Int) = {
    val scheduledFlightQuery = for {
      dbScheduledFlight <- slickScheduledFlights.filter(_.id === id)
    } yield dbScheduledFlight
    db.run(scheduledFlightQuery.result.headOption).map { dbScheduledFlightOption =>
      dbScheduledFlightOption.map { scheduledFlight =>
        ScheduledFlight(
          scheduledFlight.id,
          scheduledFlight.flightId,
          scheduledFlight.date,
          scheduledFlight.economySeats,
          scheduledFlight.businessSeats
        )
      }
    }
  }

  def getAllScheduledFlights = db.run(slickScheduledFlights.result)

  /**
    * Saves a scheduledFlight.
    *
    * @param scheduledFlight The scheduledFlight to save.
    * @return The saved scheduledFlight.
    */
  def save(scheduledFlight: ScheduledFlight) = {
    val dbScheduledFlight = ScheduledFlight(
      scheduledFlight.id,
      scheduledFlight.flightId,
      scheduledFlight.date,
      scheduledFlight.economySeats,
      scheduledFlight.businessSeats
    )

    // combine database actions to be run sequentially
    val actions = (for {
      _ <- slickScheduledFlights.insertOrUpdate(dbScheduledFlight)
    } yield ()).transactionally
    // run actions and return user afterwards
    db.run(actions).map(_ => scheduledFlight)
  }

  /**
    * Remove a scheduledFlight.
    *
    * @param id of the scheduledFlight
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit] = db.run(queryById(id).delete).map(_ => ())
}
