package controllers

import java.text.SimpleDateFormat
import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models._
import models.services.{FlightServiceImpl, FlightService}
import play.api.Logger
import play.api.i18n.MessagesApi
import utils.StringHelper.ToJson
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsValue, JsObject, JsArray, Json}
import java.sql.Date
import play.api.http.ContentTypeOf


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
        val d = new SimpleDateFormat("yyyy-MM-dd").parse(params("departureDate"))
        departureDate = Some(new java.sql.Date(d.getTime))
        //println(departureDate)
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