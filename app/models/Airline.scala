package models

import play.api.libs.json.Json


case class Airline(
                    id: Option[Int],
                    iata: String,
                    icao : String,
                    airlineName : String,
                    countryName : String
                  )


object Airline {
  /**
    * Converts the [Ticket] object to Json and vice versa.
    */
  implicit val writer = Json.writes[Airline]
  implicit val reader = Json.reads[Airline]
}