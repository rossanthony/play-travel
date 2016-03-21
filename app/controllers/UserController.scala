//package controllers
//
//import models.{User}
//import play.api.mvc._
//import play.api.libs.json._
//import scala.concurrent.Future
//import services.{UserService}
//import scala.concurrent.ExecutionContext.Implicits.global
//import javax.inject.Inject
//
//
//
//class UserController @Inject()(userService: UserService) extends Controller {
//
//  def index = Action.async { implicit request =>
//    userService.listAllUsers map { users =>
//      Ok(Json.toJson(users))
//    }
//  }
//
//  def getUser(id: Int) = Action.async { implicit request =>
//    userService.getUser(id) map { user =>
//      Ok(Json.toJson(user))
//    }
//  }
//
//  def addUser() = Action.async { implicit request =>
//    val json = request.body.asJson.get
//    val newUser = json.as[User]
//    println(newUser)
////    val newUser = User(0, data.firstName, data.lastName, data.email)
//    userService.addUser(newUser).map(res =>
//      Ok(Json.toJson(Map("status" -> "OK", "message" -> "done")))
//    )
//  }
//
//  def deleteUser(id: Int) = Action.async { implicit request =>
//    userService.deleteUser(id) map { res =>
//      Ok(Json.toJson(Map("status" -> "OK", "message" -> "done")))
//    }
//  }
//
//}