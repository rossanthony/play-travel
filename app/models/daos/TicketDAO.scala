package models.daos

import models.Ticket
import scala.concurrent.Future

/**
  * Give access to the user object.
  */
trait TicketDAO {

  /**
    * Finds a ticket by id.
    *
    * @param id The id of the ticket.
    * @return The found ticket or None if no ticket found.
    */
  def find(id: Int): Future[Option[Ticket]]


  def getAllTickets: Future[Seq[Ticket]]


  /**
    * Saves a ticket.
    *
    * @param ticket The ticket to save.
    * @return The saved ticket.
    */
  def save(ticket: Ticket): Future[Ticket]

  /**
    * Remove a ticket.
    *
    * @param id of the ticket
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit]
}
