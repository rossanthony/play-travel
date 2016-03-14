package play.travel.models

import scala.concurrent.Future
import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

case class User(
                 id: Option[Int],
                 username : String,
                 password : String,
                 firstName : String,
                 lastName : String,
                 email : String,
                 address : String,
                 townCity : String,
                 county : String,
                 postcode : String,
                 country : String,
                 cardNumber : Int,
                 expDate : Int,
                 isAdmin : Boolean
               )

/* Table mapping
 */
class UserTable(tag: Tag) extends Table[User](tag, "USER") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.NotNull)
  def password = column[String]("password", O.NotNull)
  def firstName = column[String]("firstName", O.Nullable)
  def lastName = column[String]("lastName", O.Nullable)
  def email = column[String]("email", O.Nullable)
  def address = column[String]("address", O.Nullable)
  def townCity = column[String]("townCity", O.Nullable)
  def county = column[String]("county", O.Nullable)
  def postcode = column[String]("postcode", O.Nullable)
  def country = column[String]("country", O.Nullable)
  def cardNumber = column[Int]("cardNumber", O.Nullable)
  def expDate = column[Int]("expDate", O.Nullable)
  def isAdmin = column[Boolean]("isAdmin", O.NotNull)

  // simple unique index
  def idx = index("unique_username", username, unique = true)

  // define the "shape" of a single data record
  // what should be returned when we query
  def * = (id.?, username, password, firstName, lastName, email, address, townCity, county, postcode, country, cardNumber, expDate, isAdmin) <> ((User.apply _).tupled, User.unapply _)
  //def * = (id.?, username) <> ((User.apply _).tupled, User.unapply _)


}

object User {
  implicit val userFormat = Json.format[User]
  implicit val writer = Json.writes[User]
  implicit val reader = Json.reads[User]
}
