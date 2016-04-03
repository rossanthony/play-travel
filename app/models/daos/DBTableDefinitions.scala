package models.daos

import models.{Airline, Airport, Flight, ScheduledFlight, Ticket, Booking}
import com.mohiva.play.silhouette.api.LoginInfo
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape.proveShapeOf
import java.sql.{Date, Timestamp}
import slick.profile.SqlProfile.ColumnOption.SqlType


trait DBTableDefinitions {
  
  protected val driver: JdbcProfile
  import driver.api._

  /**
    * App specific Database definitions...
    */

  class Flights(tag: Tag) extends Table[Flight](tag, "flight") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def flightNumber = column[Int]("flightNumber")
    def airlineId = column[Int]("airlineId")
    def departureAirportId = column[Int]("departureAirportId")
    def departureDay = column[Int]("departureDay")
    def departureTime = column[Int]("departureTime")
    def arrivalAirportId = column[Int]("arrivalAirportId")
    def arrivalDay = column[Int]("arrivalDay")
    def arrivalTime = column[Int]("arrivalTime")
    def economyCost = column[Int]("economyCost")
    def businessCost = column[Int]("businessCost")
    def * = (id.?, flightNumber, airlineId, departureAirportId, departureDay, departureTime, arrivalAirportId, arrivalDay, arrivalTime, economyCost, businessCost) <> ((Flight.apply _).tupled, Flight.unapply)
    // Relations
    def departureAirport = foreignKey("departure_airport_fk", departureAirportId, slickAirports)(_.id)
    def arrivalAirport = foreignKey("arrival_airport_fk", arrivalAirportId, slickAirports)(_.id)
    def airline = foreignKey("airline_fk", airlineId, slickAirlines)(_.id)
  }

  class ScheduledFlights(tag: Tag) extends Table[ScheduledFlight](tag, "scheduled_flight") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def flightId = column[Int]("flightId")
    def date = column[Date]("date")
    def economySeats = column[Int]("economySeats")
    def businessSeats = column[Int]("businessSeats")
    def * = (id.?, flightId, date, economySeats, businessSeats) <> ((ScheduledFlight.apply _).tupled, ScheduledFlight.unapply)
    // Relations
    def flight = foreignKey("flight_fk", flightId, slickFlights)(_.id)
  }

  class Tickets(tag: Tag) extends Table[Ticket](tag, "ticket") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def bookingId = column[Int]("bookingId")
    def flightId = column[Int]("flightId")
    def ticketType = column[String]("ticketType")
    def status = column[String]("status")
    def * = (id.?, bookingId, flightId, ticketType, status) <> ((Ticket.apply _).tupled, Ticket.unapply)
  }

  class Bookings(tag: Tag) extends Table[Booking](tag, "booking") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[String]("userId")
    def status = column[String]("status")
    def created = column[Timestamp]("created", SqlType("timestamp not null default CURRENT_TIMESTAMP"))
    def updated = column[Timestamp]("updated", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))
    def * = (id.?, userId, status, created.?, updated.?) <> ((Booking.apply _).tupled, Booking.unapply)
  }

  class Airlines(tag: Tag) extends Table[Airline](tag, "airline") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def iata = column[String]("iata")
    def icao = column[String]("icao")
    def airlineName = column[String]("airlineName")
    def countryName = column[String]("countryName")
    def * = (id.?, iata, icao, airlineName, countryName) <> ((Airline.apply _).tupled, Airline.unapply)
  }

  class Airports(tag: Tag) extends Table[Airport](tag, "airport") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def code = column[String]("code")
    def name = column[String]("name")
    def cityCode = column[String]("cityCode")
    def cityName = column[String]("cityName")
    def countryName = column[String]("countryName")
    def countryCode = column[String]("countryCode")
    def timezone = column[String]("timezone")
    def lat = column[String]("lat")
    def lon = column[String]("lon")
    def numAirports = column[Int]("numAirports")
    def city = column[Boolean]("city")
    def * = (id.?, code, name, cityCode, cityName, countryName, countryCode, timezone, lat, lon, numAirports, city) <> ((Airport.apply _).tupled, Airport.unapply)
  }

  /**
    * Silhouette (user auth) specific Database definitions...
    */

  case class DBUser (
    userID: String,
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
  )

  class DBUsers(tag: Tag) extends Table[DBUser](tag, "user") {
    def id = column[String]("userID", O.PrimaryKey)
    def firstName = column[Option[String]]("firstName")
    def lastName = column[Option[String]]("lastName")
    def fullName = column[Option[String]]("fullName")
    def email = column[Option[String]]("email")
    def avatarURL = column[Option[String]]("avatarURL")
    def addressLine1 = column[Option[String]]("addressLine1")
    def addressLine2 = column[Option[String]]("addressLine2")
    def townCity = column[Option[String]]("townCity")
    def country = column[Option[String]]("country")
    def postcode = column[Option[String]]("postcode")
    def telephone = column[Option[String]]("telephone")
    def cardType = column[Option[String]]("cardType")
    def cardNumber = column[Option[Int]]("cardNumber")
    def expDate = column[Option[Int]]("expDate")

    def * = (id, firstName, lastName, fullName, email, avatarURL, addressLine1, addressLine2, townCity, country, postcode, telephone, cardType, cardNumber, expDate) <> (DBUser.tupled, DBUser.unapply)
  }

  case class DBLoginInfo (
    id: Option[Long],
    providerID: String,
    providerKey: String
  )

  class LoginInfos(tag: Tag) extends Table[DBLoginInfo](tag, "logininfo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def providerID = column[String]("providerID")
    def providerKey = column[String]("providerKey")
    def * = (id.?, providerID, providerKey) <> (DBLoginInfo.tupled, DBLoginInfo.unapply)
  }

  case class DBUserLoginInfo (
    userID: String,
    loginInfoId: Long
  )

  class UserLoginInfos(tag: Tag) extends Table[DBUserLoginInfo](tag, "userlogininfo") {
    def userID = column[String]("userID")
    def loginInfoId = column[Long]("loginInfoId")
    def * = (userID, loginInfoId) <> (DBUserLoginInfo.tupled, DBUserLoginInfo.unapply)
  }

  case class DBPasswordInfo (
    hasher: String,
    password: String,
    salt: Option[String],
    loginInfoId: Long
  )

  class PasswordInfos(tag: Tag) extends Table[DBPasswordInfo](tag, "passwordinfo") {
    def hasher = column[String]("hasher")
    def password = column[String]("password")
    def salt = column[Option[String]]("salt")
    def loginInfoId = column[Long]("loginInfoId")
    def * = (hasher, password, salt, loginInfoId) <> (DBPasswordInfo.tupled, DBPasswordInfo.unapply)
  }

  case class DBOAuth1Info (
    id: Option[Long],
    token: String,
    secret: String,
    loginInfoId: Long
  )

  class OAuth1Infos(tag: Tag) extends Table[DBOAuth1Info](tag, "oauth1info") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def token = column[String]("token")
    def secret = column[String]("secret")
    def loginInfoId = column[Long]("loginInfoId")
    def * = (id.?, token, secret, loginInfoId) <> (DBOAuth1Info.tupled, DBOAuth1Info.unapply)
  }

  case class DBOAuth2Info (
    id: Option[Long],
    accessToken: String,
    tokenType: Option[String],
    expiresIn: Option[Int],
    refreshToken: Option[String],
    loginInfoId: Long
  )

  class OAuth2Infos(tag: Tag) extends Table[DBOAuth2Info](tag, "oauth2info") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def accessToken = column[String]("accesstoken")
    def tokenType = column[Option[String]]("tokentype")
    def expiresIn = column[Option[Int]]("expiresin")
    def refreshToken = column[Option[String]]("refreshtoken")
    def loginInfoId = column[Long]("logininfoid")
    def * = (id.?, accessToken, tokenType, expiresIn, refreshToken, loginInfoId) <> (DBOAuth2Info.tupled, DBOAuth2Info.unapply)
  }
  
  case class DBOpenIDInfo (
    id: String,
    loginInfoId: Long
  )
  
  class OpenIDInfos(tag: Tag) extends Table[DBOpenIDInfo](tag, "openidinfo") {
    def id = column[String]("id", O.PrimaryKey)
    def loginInfoId = column[Long]("logininfoid")
    def * = (id, loginInfoId) <> (DBOpenIDInfo.tupled, DBOpenIDInfo.unapply)
  }
  
  case class DBOpenIDAttribute (
    id: String,
    key: String,
    value: String
  )
  
  class OpenIDAttributes(tag: Tag) extends Table[DBOpenIDAttribute](tag, "openidattributes") {
    def id = column[String]("id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (id, key, value) <> (DBOpenIDAttribute.tupled, DBOpenIDAttribute.unapply)
  }


  // table query definitions
  val slickUsers = TableQuery[DBUsers]
  val slickLoginInfos = TableQuery[LoginInfos]
  val slickUserLoginInfos = TableQuery[UserLoginInfos]
  val slickPasswordInfos = TableQuery[PasswordInfos]
  val slickOAuth1Infos = TableQuery[OAuth1Infos]
  val slickOAuth2Infos = TableQuery[OAuth2Infos]
  val slickOpenIDInfos = TableQuery[OpenIDInfos]
  val slickOpenIDAttributes = TableQuery[OpenIDAttributes]
  val slickFlights = TableQuery[Flights]
  val slickScheduledFlights = TableQuery[ScheduledFlights]
  val slickTickets = TableQuery[Tickets]
  val slickBookings = TableQuery[Bookings]
  val slickAirlines = TableQuery[Airlines]
  val slickAirports = TableQuery[Airports]

  // queries used in multiple places
  def loginInfoQuery(loginInfo: LoginInfo) = 
    slickLoginInfos.filter(dbLoginInfo => dbLoginInfo.providerID === loginInfo.providerID && dbLoginInfo.providerKey === loginInfo.providerKey)
}
