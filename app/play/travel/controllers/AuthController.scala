package play.travel.controllers

import play.api.mvc._
import play.travel.actors.messages.{CreateEntity, ReadEntity}
import play.travel.controllers.UserController._
import play.travel.traits._

import play.travel.actors._
import play.travel.models.User
import play.api.mvc.Action
import play.api.libs.json.Json
import akka.pattern.ask
import scala.concurrent._
import ExecutionContext.Implicits.global


object AuthController extends Controller with Secured {

  def login() = Action.async(parse.json) { request =>
    val username = (request.body \ "username").as[String]
    val password = (request.body \ "password").as[String]

    (userActor ? new FindEntityByEmail(username)).map(user => {
      user match {
        case Some(u: User) =>
          if (u.password == password) {
            // Create a new session, send back the session_id (cookie)

            Ok(Json.toJson(
              Map("status" -> "OK")
            )).withSession(
                "id" -> u.id.toString
              )
          } else {
            Unauthorized
          }
        case None =>
          Ok(Json.toJson(
            Map("status" -> "KO", "error" -> "password and username cannot be blank")
          ))
      }
    })
  }

//  def login(email: String) = Action.async { request =>
//    (userActor ? new FindEntityByEmail(email)).map(user => {
//      user match {
//        case Some(u: User) =>
//          Ok(Json.toJson(u))
//        case None =>
//          BadRequest
//      }
//    })
//  }

  def publicApi = Action {
    Ok("That was easy!")
  }

  def privateApi = Authenticated {
    Ok("Only the best can see that.")
  }

  def adminApi = Admin {
    Ok("Top secret data. Hopefully, nobody will ever access it.")
  }

}
