package models

import java.text.SimpleDateFormat

import play.api.libs.functional.syntax._
import play.api.libs.json._
import java.sql.Date

case class ScheduledFlight(
                   id: Option[Int],
                   flightId : Int,
                   date : Date,
//                   departureDate : Date,
//                   arrivalDate : Date,
                   economySeats : Int,
                   businessSeats : Int)


object ScheduledFlight {
  /**
    * Converts the [ScheduledFlight] object to Json and vice versa.
    */
//  implicit val writer = Json.writes[ScheduledFlight]
  //implicit val reader = Json.reads[ScheduledFlight]

  implicit val writer: Writes[ScheduledFlight] = (
      (JsPath \ "id").write[Option[Int]] and
      (JsPath \ "flightId").write[Int] and
      (JsPath \ "date").write[String].contramap{ (a: Date) => a.toString } and
      //(JsPath \ "date").write[Date] and
      (JsPath \ "economySeats").write[Int] and
      (JsPath \ "businessSeats").write[Int]
    )(unlift(ScheduledFlight.unapply))


  implicit val readerDates: Reads[Date] = (__ \ "date").read[String].map{
    dateStr => {
      val date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
      new Date(date.getTime)
    }
  }
  implicit val writerDates: Writes[Date] = (__ \ "date").write[String].contramap{ (a: Date) => a.toString }
  implicit val format: Format[Date] = Format(readerDates, writerDates)



}


case class FlightSearchResult(f: Flight, sf: ScheduledFlight, a: Airline, da: Airport, aa: Airport)
