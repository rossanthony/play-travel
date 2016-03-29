package models

import play.api.libs.json.Json


case class Airport(
                    id: Option[Int],
                    code: String,
                    name: String,
                    cityCode: String,
                    cityName: String,
                    countryName: String,
                    countryCode: String,
                    timezone: String,
                    lat: String,
                    lon: String,
                    numAirports: Int,
                    city: Boolean
                  )

object Airport {
  /**
    * Converts the [Ticket] object to Json and vice versa.
    */
  implicit val writer = Json.writes[Airport]
  implicit val reader = Json.reads[Airport]
}