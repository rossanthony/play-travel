package play.travel.models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

case class User(id: Option[Int], username : String, password : String, firstName : String, lastName : String, email : String)

/* Table mapping
 */
class UserTable(tag: Tag) extends Table[User](tag, "USER") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.NotNull)
  def password = column[String]("password", O.NotNull)
  def firstName = column[String]("firstName", O.NotNull)
  def lastName = column[String]("lastName", O.NotNull)
  def email = column[String]("email", O.NotNull)

  // simple unique index
  def idx = index("unique_username", username, unique = true)

  // define the "shape" of a single data record
  // what should be returned when we query
  def * = (id.?, username, password, firstName, lastName, email) <> ((User.apply _).tupled, User.unapply _)
}
object User {
  implicit val writer = Json.writes[User]
  implicit val reader = Json.reads[User]
}
