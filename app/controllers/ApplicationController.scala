package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, LogoutEvent, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.User
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import utils.StringHelper.ToJson
import play.api.mvc._

import scala.concurrent.Future

/**
  * The basic application controller.
  *
  * @param messagesApi The Play messages API.
  * @param env The Silhouette environment.
  * @param socialProviderRegistry The social provider registry.
  */
class ApplicationController @Inject() (
                                        val messagesApi: MessagesApi,
                                        val env: Environment[User, JWTAuthenticator],
                                        socialProviderRegistry: SocialProviderRegistry)
  extends Silhouette[User, JWTAuthenticator] {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index())
  }

  def anyPath(path: String) = Action {
    Ok(views.html.index())
  }

  /**
    * Returns the user.
    *
    * @return The result to display.
    */
//  def user = SecuredAction.async { implicit request =>
//    Future.successful(Ok(Json.toJson(request.identity)))
//  }
  def getMe = UserAwareAction.async { implicit request =>
    Future.successful({
      request.identity match {
        case Some(user) =>
          Ok(Map[String, Any](
            "status" -> true,
            "message" -> s"Hello, ${user.fullName} <${user.email}>"
          ).toJson)
        case None =>
          Unauthorized(
            Map[String, Any](
              "status" -> false,
              "message" -> "user.not_authorizaed"
            ).toJson)
      }
    }.as("application/json"))
  }

  /**
    * Manages the sign out action.
    */
  def signOut = SecuredAction.async { implicit request =>
    env.eventBus.publish(LogoutEvent(request.identity, request, request2Messages))
    env.authenticatorService.discard(request.authenticator, Ok)
  }

  /**
    * Provides the desired template.
    *
    * @param template The template to provide.
    * @return The template.
    */
  def view(template: String) = UserAwareAction { implicit request =>
    template match {
      case "home" => Ok(views.html.home())
      case "signUp" => Ok(views.html.signUp())
      case "signIn" => Ok(views.html.signIn(socialProviderRegistry))
      case "navigation" => Ok(views.html.navigation())
      case _ => NotFound
    }
  }
}