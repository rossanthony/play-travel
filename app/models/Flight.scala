package models

import play.api.libs.json.Json

case class Flight(
  id: Option[Int],
  flightNumber : Int,
  airlineId : Int,
  departureAirportId : Int,
  departureDay : Int,
  departureTime : Int,
  arrivalAirportId : Int,
  arrivalDay : Int,
  arrivalTime : Int,
  economyCost : Int,
  businessCost : Int)


object Flight {
  /**
    * Converts the [Flight] object to Json and vice versa.
    */
  implicit val writer = Json.writes[Flight]
  implicit val reader = Json.reads[Flight]
}