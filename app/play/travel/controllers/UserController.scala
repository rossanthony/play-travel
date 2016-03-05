package play.travel.controllers

import akka.actor.Props
import play.travel.actors.UserActor
import play.travel.actors.messages._
import play.travel.models.User
import play.api.libs.concurrent.Akka
import play.api.mvc.Action
import play.api.Play.current
import play.api.libs.json.Json
import akka.pattern.ask
import scala.concurrent._
import ExecutionContext.Implicits.global


object UserController extends BaseController {

  val userActor = Akka.system.actorOf(Props[UserActor], name = "useractor")

  def create() = Action.async { request =>
    val data = request.body.asJson.get
    val user = Json.fromJson[User](data).getOrElse(throw new RuntimeException("Incorrect json"))
    (userActor ? new CreateEntity[User](user)).map(id => {
      Ok(Json.toJson(id.asInstanceOf[Int]))
    })
  }

  def update() = Action.async { request =>
    val data = request.body.asJson.get
    val user = Json.fromJson[User](data).getOrElse(throw new RuntimeException("Incorrect json"))
    (userActor ? new UpdateEntity[User](user)).map(affectedRows => {
      Ok(Json.toJson(affectedRows.asInstanceOf[Int]))
    })
  }

  def read(id: Int) = Action.async { request =>
    (userActor ? new ReadEntity(id)).map(user => {
      user match {
        case Some(u: User) =>
          Ok(Json.toJson(u))
        case None =>
          BadRequest
      }
    })
  }

  def list() = Action.async { request =>
    (userActor ? new ListEntities()).map(users => {
      Ok(Json.toJson(users.asInstanceOf[List[User]]))
    })
  }

  def delete(id: Int) = Action.async { request =>
    (userActor ? new DeleteEntity(id)).map(affectedRows => {
      Ok(Json.toJson(affectedRows.asInstanceOf[Int]))
    })
  }

}
