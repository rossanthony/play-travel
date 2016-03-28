package models.daos

import models.Booking
import scala.concurrent.Future

/**
  * Give access to the user object.
  */
trait BookingDAO {

  /**
    * Finds a booking by id.
    *
    * @param id The id of the booking.
    * @return The found booking or None if no booking found.
    */
  def find(id: Int): Future[Option[Booking]]


  def getAllBookings: Future[Seq[Booking]]


  /**
    * Saves a booking.
    *
    * @param booking The booking to save.
    * @return The saved booking.
    */
  def save(booking: Booking): Future[Booking]

  /**
    * Remove a booking.
    *
    * @param id of the booking
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit]
}
