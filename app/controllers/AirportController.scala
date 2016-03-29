package controllers

import javax.inject.Inject
import models.services.AirportServiceImpl
import play.api.mvc._
import utils.StringHelper.ToJson
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * The airport controller.
  */
class AirportController @Inject()(airportService: AirportServiceImpl) extends Controller {

  def getAllAirports: Action[AnyContent] = Action.async { implicit request =>
    val resultFuture = airportService.list
    resultFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson)
    }.recover {
      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"No airports found!").toJson)
    }
  }
}