package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry

import models.User
import models.services.{FlightServiceImpl, FlightService}
import play.api.i18n.MessagesApi
import utils.StringHelper.ToJson
import play.api.mvc._
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, LogoutEvent, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.User
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import utils.StringHelper.ToJson
import play.api.mvc._

import scala.concurrent.Future

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
    * Return details of a single flight, by id.
    */
  def getFlight(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = flightService.retrieve(id)
    flightFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson)
    }.recover {
      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"Flight not found with id:$id").toJson)
    }
  }

  def getAllFlights: Action[AnyContent] = Action.async { implicit request =>
    val flightFuture = flightService.getAllFlights
    flightFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson)
    }.recover {
      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"No flights found!").toJson)
    }
  }


  def deleteFlight(id: Int) = UserAwareAction.async { implicit request =>
    Future.successful({
      request.identity match {
        case Some(user) =>
          flightService.delete(id)
          Ok(Map[String, Any]("status" -> "OK").toJson)
        case None =>
          Unauthorized(Map[String, Any]("status" -> false, "message" -> "user.not-authorized").toJson)
      }
    }.as("application/json"))
  }

  /**
    * Perform a search for flights.
    */
  def search: Action[AnyContent] = Action.async { implicit request =>
    // gather search criteria from query string
    val params = request.queryString.map { case (k,v) => k -> v.mkString }


    var arrivalLocation = None: Option[String]
    if (params.contains("arrivalLocation")) {
      arrivalLocation = Some(params("arrivalLocation").toString)
    }

    var departureLocation = None: Option[String]
    if (params.contains("departureLocation")) {
      departureLocation = Some(params("departureLocation").toString)
    }

    val resultingFlights = flightService.search(departureLocation, arrivalLocation)
    resultingFlights.map(flights => Ok(Map[String, Any]("status" -> "OK", "data" -> flights).toJson))
  }

}