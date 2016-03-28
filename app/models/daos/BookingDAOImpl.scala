package models.daos

import models.Booking
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

/**
  * Give access to the booking object using Slick
  */
class BookingDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends BookingDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickBookings.filter(_.id === id))

  /**
    * Finds a booking by its id.
    *
    * @param id The ID of the booking.
    * @return The found booking or None if no booking found with given id.
    */
  def find(id: Int) = {
    val bookingQuery = for {
      dbBooking <- slickBookings.filter(_.id === id)
    } yield dbBooking
    db.run(bookingQuery.result.headOption).map { dbBookingOption =>
      dbBookingOption.map { booking =>
        Booking(
          booking.id,
          booking.userId,
          booking.status,
          booking.created,
          booking.updated
        )
      }
    }
  }

  def getAllBookings = db.run(slickBookings.result)

  /**
    * Saves a booking.
    *
    * @param booking The booking to save.
    * @return The saved booking.
    */
  def save(booking: Booking) = {
    val dbBooking = Booking(
      booking.id,
      booking.userId,
      booking.status,
      booking.created,
      booking.updated
    )

    // combine database actions to be run sequentially
    val actions = (for {
      _ <- slickBookings.insertOrUpdate(dbBooking)
    } yield ()).transactionally
    // run actions and return user afterwards
    db.run(actions).map(_ => booking)
  }

  /**
    * Remove a booking.
    *
    * @param id of the booking
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit] = db.run(queryById(id).delete).map(_ => ())
}
