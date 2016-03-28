package models

import play.api.libs.json.Json
import java.sql.Timestamp


/**
  * Each Booking is made up of one or more Ticket(s)
  * If it's a single trip, then obviously it will consist of just the one Ticket.
  * Returns will have two Tickets and so on...
  */
case class Booking(
                   id: Option[Int],
                   userId: String,
                   status : String,
                   created: Option[Timestamp],
                   updated: Option[Timestamp]
                 )


object Booking {
  /**
    * Converts the [Ticket] object to Json and vice versa.
    */
  implicit val writer = Json.writes[Ticket]
  implicit val reader = Json.reads[Ticket]
}