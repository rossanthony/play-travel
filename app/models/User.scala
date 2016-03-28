package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }
import play.api.libs.json.Json

/**
 * The user object.
 *
 * @param userID The unique ID of the user.
 * @param loginInfo The linked login info.
 * @param firstName Maybe the first name of the authenticated user.
 * @param lastName Maybe the last name of the authenticated user.
 * @param fullName Maybe the full name of the authenticated user.
 * @param email Maybe the email of the authenticated provider.
 * @param avatarURL Maybe the avatar URL of the authenticated provider.
 * @param addressLine1 Maybe line 1 of the address
 * @param addressLine2 Maybe line 2 of the address
 * @param townCity Maybe the town or city
 * @param country Maybe the country
 * @param postcode Maybe the postcode
 * @param telephone Maybe the telephone number
 * @param cardType Maybe the card type
 * @param cardNumber Maybe the card number
 * @param expDate Maybe the expiry date
 */
case class User(
  userID: UUID,
  loginInfo: LoginInfo,
  firstName: Option[String],
  lastName: Option[String],
  fullName: Option[String],
  email: Option[String],
  avatarURL: Option[String],
  addressLine1: Option[String],
  addressLine2: Option[String],
  townCity: Option[String],
  country: Option[String],
  postcode: Option[String],
  telephone: Option[String],
  cardType: Option[String],
  cardNumber: Option[Int],
  expDate: Option[Int]
) extends Identity


/**
  * The companion object.
  */
object User {

  /**
    * Converts the [User] object to Json and vice versa.
    */
  implicit val jsonFormat = Json.format[User]
}