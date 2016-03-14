package play.travel.traits

import scala.concurrent.Future
import play.api.mvc._
import play.api.mvc.Results._
import play.travel.models.User
import play.api.libs.json._

class AuthenticatedRequest[A](val user: User, request: Request[A]) extends WrappedRequest[A](request)

trait Secured {
  def Authenticated = AuthenticatedAction
  def Admin = AdminAction
}

object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]) = {
    println(request.session.get("id"))
    Future.successful(Unauthorized)
  }
//    request.jwtSession.getAs[User]("user") match {
//    request.cookies.get("test") match {
//      case Some(user) => block(new AuthenticatedRequest(user, request)).map(_.refreshJwtSession(request))
//      case _ => Future.successful(Unauthorized)
//    }
}

object AdminAction extends ActionBuilder[AuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]) =
    Future.successful(Unauthorized)
//    request.jwtSession.getAs[User]("user") match {
//      case Some(user) if user.isAdmin => block(new AuthenticatedRequest(user, request)).map(_.refreshJwtSession(request))
//      case Some(_) => Future.successful(Forbidden.refreshJwtSession(request))
//      case _ => Future.successful(Unauthorized)
//    }
}

