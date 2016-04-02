package models

import play.api.libs.json.Json
import java.sql.Date

case class ScheduledFlight(
                   id: Option[Int],
                   flightId : Int,
                   date : Date,
                   economySeats : Int,
                   businessSeats : Int)


object ScheduledFlight {
  /**
    * Converts the [ScheduledFlight] object to Json and vice versa.
    */
  implicit val writer = Json.writes[ScheduledFlight]
  implicit val reader = Json.reads[ScheduledFlight]
}