//package controllers
//
//import javax.inject._
//import play.api.mvc._
//import play.api.libs.json.Json
//import models.Credentials
//import services.UserService
//import scala.concurrent.ExecutionContext.Implicits.global
//
//
///**
// * This controller defines the REST endpoints for authentication
// */
//@Singleton
//class AuthController @Inject()(userService: UserService) extends Controller {
//
//  def login() = Action.async { implicit request =>
//    val json = request.body.asJson.get
//    val creds = json.as[Credentials]
//    println(creds.email)
//    println(creds.password)
//
//
//    userService.login(creds) map { user =>
//      if (user) {
//        Ok(Json.toJson(Map("status" -> "OK", "message" -> "User successfully logged in")))
//      } else {
//        Ok(Json.toJson(Map("status" -> "KO", "message" -> "User not found")))
//      }
//    }
//
////    userService.listAllUsers map { users =>
////      Ok(Json.toJson(users))
////    }
//  }
//
//}
