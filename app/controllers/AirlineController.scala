package controllers

import javax.inject.Inject
import models.services.AirlineServiceImpl
import play.api.mvc._
import utils.StringHelper.ToJson
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * The airline controller.
  */
class AirlineController @Inject()(airlineService: AirlineServiceImpl) extends Controller {

  def getAllAirlines: Action[AnyContent] = Action.async { implicit request =>
    val resultFuture = airlineService.list
    resultFuture.map {
      result => Ok(Map[String, Any]("status" -> "OK", "data" -> result).toJson)
    }.recover {
      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"No airlines found!").toJson)
    }
  }
}