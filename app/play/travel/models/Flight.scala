package play.travel.models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

case class Flight(id: Option[Int], flightNumber : Int, airlineCode : String, airlineName : String, departureLocation : String, departureDay : Int, departureTime : Int, arrivalLocation : String, arrivalDay : Int, arrivalTime : Int, economyCost : Int, businessCost : Int)

/* Table mapping
 */
class FlightTable(tag: Tag) extends Table[Flight](tag, "FLIGHT") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def flightNumber = column[Int]("flightNumber", O.NotNull)
  def airlineCode = column[String]("airlineCode", O.NotNull)
  def airlineName = column[String]("airlineName", O.NotNull)
  def departureLocation = column[String]("departureLocation", O.NotNull)
  def departureDay = column[Int]("departureDay", O.NotNull)
  def departureTime = column[Int]("departureTime", O.NotNull)
  def arrivalLocation = column[String]("arrivalLocation", O.NotNull)
  def arrivalDay = column[Int]("arrivalDay", O.NotNull)
  def arrivalTime = column[Int]("arrivalTime", O.NotNull)
  def economyCost = column[Int]("economyCost", O.NotNull)
  def businessCost = column[Int]("businessCost", O.NotNull)

  def * = (id.?, flightNumber, airlineCode, airlineName, departureLocation, departureDay, departureTime, arrivalLocation, arrivalDay, arrivalTime, economyCost, businessCost) <> ((Flight.apply _).tupled, Flight.unapply _)
}
object Flight {
  implicit val writer = Json.writes[Flight]
  implicit val reader = Json.reads[Flight]
}
