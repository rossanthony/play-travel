package models

import play.api.libs.json.Json

/**
  * Each Booking is made up of one or more Ticket(s)
  * If it's a single trip, then obviously it will consist of just the one Ticket.
  * Returns will have two Tickets and so on...
  */
case class Ticket(
                   id: Option[Int],
                   bookingId : Int,
                   flightId : Int,
                   ticketType : String,
                   status : String
                   )


object Ticket {
  /**
    * Converts the [Ticket] object to Json and vice versa.
    */
  implicit val writer = Json.writes[Ticket]
  implicit val reader = Json.reads[Ticket]
}