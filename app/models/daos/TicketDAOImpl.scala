package models.daos

import models.Ticket
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

/**
  * Give access to the ticket object using Slick
  */
class TicketDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends TicketDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickTickets.filter(_.id === id))

  /**
    * Finds a ticket by its id.
    *
    * @param id The ID of the ticket.
    * @return The found ticket or None if no ticket found with given id.
    */
  def find(id: Int) = {
    val ticketQuery = for {
      dbTicket <- slickTickets.filter(_.id === id)
    } yield dbTicket
    db.run(ticketQuery.result.headOption).map { dbTicketOption =>
      dbTicketOption.map { ticket =>
        Ticket(
          ticket.id,
          ticket.bookingId,
          ticket.flightId,
          ticket.ticketType,
          ticket.status
        )
      }
    }
  }

  def getAllTickets = db.run(slickTickets.result)

  /**
    * Saves a ticket.
    *
    * @param ticket The ticket to save.
    * @return The saved ticket.
    */
  def save(ticket: Ticket) = {
    val dbTicket = Ticket(
      ticket.id,
      ticket.bookingId,
      ticket.flightId,
      ticket.ticketType,
      ticket.status
    )

    // combine database actions to be run sequentially
    val actions = (for {
      _ <- slickTickets.insertOrUpdate(dbTicket)
    } yield ()).transactionally
    // run actions and return user afterwards
    db.run(actions).map(_ => ticket)
  }

  /**
    * Remove a ticket.
    *
    * @param id of the ticket
    * @return A future to wait for the process to be completed.
    */
  def remove(id: Int): Future[Unit] = db.run(queryById(id).delete).map(_ => ())
}
