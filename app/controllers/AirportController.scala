package controllers

import javax.inject.Inject
import models.{Airport, Airline, ScheduledFlight, Flight}
import models.services.AirportServiceImpl
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.functional.syntax._
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

  def getAllAirportsList: Action[AnyContent] = Action.async { implicit request =>

    val itemWrites: OWrites[(String, String, String)] = (
        (__ \ "code").write[String] and
        (__ \ "name").write[String] and
        (__ \ "country").write[String]
      ).tupled

    val resultWrites: Writes[Seq[(String, String, String)]] = Writes.seq(itemWrites)

    airportService.listForSelect.map {
      results =>
        Ok(
          Json.obj(
            "status" -> "Ok",
            "data" -> resultWrites.writes(results)
          )
        ).as("application/json")
    }.recover {
      case _ => Ok(Map[String, Any]("status" -> "KO", "message" -> s"No airports found!").toJson)
    }
  }
}