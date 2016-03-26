package controllers

import javax.inject.Inject

import models.Flight
import models.services.{FlightServiceImpl, FlightService}

import play.api.libs.json.Json
import utils.StringHelper.ToJson
import play.api.mvc._
import models.daos._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * The flight controller.
  */
class FlightController @Inject() (flightService: FlightServiceImpl) extends Controller {

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

  /**
    * Perform a search for flights.
    */
  def search: Action[AnyContent] = Action.async { implicit request =>
    // gather search criteria from query string
    //request.queryString.map { case (k,v) => k -> v.mkString }
    println(request.queryString)

    val resultingFlights = flightService.search(request.queryString)
    resultingFlights.map(flights => Ok(flights.toJson))

//    val flightFuture = flightService.search(request.queryString)
//    flightFuture.map {
//      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson)
//    }.recover {
//      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"No flights found matching search criteria").toJson)
//    }
  }
}