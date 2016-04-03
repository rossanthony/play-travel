package controllers

import java.text.SimpleDateFormat
import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models._
import models.services.FlightServiceImpl
import play.api.Logger
import play.api.i18n.MessagesApi
import utils.StringHelper.ToJson
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsValue, JsObject, JsArray, Json}
import java.sql.Date

/**
  * The flight controller.
  */
class FlightController @Inject() (
                                          flightService: FlightServiceImpl,
                                          val messagesApi: MessagesApi,
                                          val env: Environment[User, JWTAuthenticator],
                                          socialProviderRegistry: SocialProviderRegistry)
    extends Silhouette[User, JWTAuthenticator] {

  /**
    * Get all info about a specific flight (with related airline, departureAirport and arrivalAirport)
    *
    * @param id of the flight
    * @return HTTP Response
    */
  def getFlight(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = flightService.retrieve(id)
    flightFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson).as("application/json")
    }.recover {
      case _ => BadRequest(Map[String, Any]("status" -> "KO", "message" -> s"Flight not found with id:$id").toJson).as("application/json")
    }
  }

  /**
    * List all flights (with related airline, departureAirport and arrivalAirport)
    *
    * @return HTTP Response
    */
  def getAllFlights: Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = flightService.getAllFlights
    flightFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson).as("application/json")
    }.recover {
      case _ => BadRequest(Map[String, Any]("status" -> "KO", "message" -> "No flights found!").toJson).as("application/json")
    }
  }

  /**
    * Delete a specified flight (must be logged in as admin user)
    *
    * @param id of the the flight
    * @return HTTP Response
    */
  def deleteFlight(id: Int) = UserAwareAction.async { implicit request =>
    Future.successful({
      request.identity match {
        case Some(user) =>
          // @TODO check if this user is admin!!

          flightService.delete(id)
          // @TODO also remove all scheduled flights associated with this flightId?

          Ok(Map[String, Any]("status" -> "OK").toJson).as("application/json")
        case None =>
          Unauthorized(Map[String, Any]("status" -> "KO", "message" -> "user.not-authorized").toJson).as("application/json")
      }
    }.as("application/json"))
  }

  /**
    * Perform a filtered search for scheduled flights.
    */
  /**
    *
    * @return HTTP Response
    */
  def search: Action[AnyContent] = Action.async { implicit request =>
    // gather search criteria from query string
    val params = request.queryString.map { case (k, v) => k -> v.mkString }

    var arrivalCity = None: Option[String]
    if (params.contains("arrivalCity"))
      arrivalCity = Some(params("arrivalCity"))

    var departureCity = None: Option[String]
    if (params.contains("departureCity"))
      departureCity = Some(params("departureCity"))

    // testVal.getOrElse[String]("")

    var departureDate = None: Option[Date]
    if (params.contains("departureDate")) {
      try {
        val pureDate = params("departureDate").split("T")
        val d = new SimpleDateFormat("yyyy-MM-dd").parse(pureDate(0))
        departureDate = Some(new java.sql.Date(d.getTime))
      } catch {
        case e: Exception => println(s"Exception: $e")
      }
    }

    // @TODO add other optional filter params, departure/arrival date/time, etc...

    flightService.search(departureCity, arrivalCity, departureDate).map(results => {
      Ok(
        Map[String, Any](
          "status" -> "OK",
          "data" -> results
        ).toJson
      ).as("application/json")
    })
  }
}