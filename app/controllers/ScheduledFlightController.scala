package controllers

import java.text.SimpleDateFormat
import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models._
import models.services.ScheduledFlightServiceImpl
import play.api.Logger
import play.api.i18n.MessagesApi
import utils.StringHelper.ToJson
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsValue, JsObject, JsArray, Json}
import java.sql.Date

/**
  * The scheduled flight controller.
  */
class ScheduledFlightController @Inject() (
                                   scheduledFlightService: ScheduledFlightServiceImpl,
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
  def getScheduledFlight(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = scheduledFlightService.retrieve(id)
    flightFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson).as("application/json")
    }.recover {
      case _ => BadRequest(Map[String, Any]("status" -> "KO", "message" -> s"ScheduledFlight not found with id:$id").toJson).as("application/json")
    }
  }

  /**
    * List all flights (with related airline, departureAirport and arrivalAirport)
    *
    * @return HTTP Response
    */
  def getAllScheduledFlights: Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = scheduledFlightService.getAllScheduledFlights
    flightFuture.map {
      result => Ok(
        //Map[String, Any]("status" -> "OK", "data" -> result).toJson
        Json.toJson(result)
      ).as("application/json")
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
  def deleteScheduledFlight(id: Int) = UserAwareAction.async { implicit request =>
    Future.successful({
      request.identity match {
        case Some(user) =>
          // @TODO check if this user is admin!!

          scheduledFlightService.delete(id)
          // @TODO also remove all scheduled flights associated with this flightId?

          Ok(Map[String, Any]("status" -> "OK").toJson).as("application/json")
        case None =>
          Unauthorized(Map[String, Any]("status" -> "KO", "message" -> "user.not-authorized").toJson).as("application/json")
      }
    }.as("application/json"))
  }

}