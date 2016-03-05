package play.travel.controllers

import akka.actor.Props
import play.travel.actors.FlightActor
import play.travel.actors.messages._
import play.travel.models.Flight
import play.api.libs.concurrent.Akka
import play.api.mvc.Action
import play.api.Play.current
import play.api.libs.json.Json
import akka.pattern.ask
import scala.concurrent._
import ExecutionContext.Implicits.global


object FlightController extends BaseController {

  val flightActor = Akka.system.actorOf(Props[FlightActor], name = "flightactor")

  def create() = Action.async { request =>
    val data = request.body.asJson.get
    val flight = Json.fromJson[Flight](data).getOrElse(throw new RuntimeException("Incorrect json"))
    (flightActor ? new CreateEntity[Flight](flight)).map(id => {
      Ok(Json.toJson(id.asInstanceOf[Int]))
    })
  }

  def update() = Action.async { request =>
    val data = request.body.asJson.get
    val flight = Json.fromJson[Flight](data).getOrElse(throw new RuntimeException("Incorrect json"))
    (flightActor ? new UpdateEntity[Flight](flight)).map(affectedRows => {
      Ok(Json.toJson(affectedRows.asInstanceOf[Int]))
    })
  }

  def read(id: Int) = Action.async { request =>
    (flightActor ? new ReadEntity(id)).map(flight => {
      flight match {
        case Some(u: Flight) =>
          Ok(Json.toJson(u))
        case None =>
          BadRequest
      }
    })
  }

  def list() = Action.async { request =>
    (flightActor ? new ListEntities()).map(flights => {
      Ok(Json.toJson(flights.asInstanceOf[List[Flight]]))
    })
  }

  def delete(id: Int) = Action.async { request =>
    (flightActor ? new DeleteEntity(id)).map(affectedRows => {
      Ok(Json.toJson(affectedRows.asInstanceOf[Int]))
    })
  }

}
