//package services
//
//import models.{Credentials, UsersDAO, User}
//import play.api.libs.json.Json
//import scala.concurrent.Future
//import javax.inject.Inject
//
//
////class Application @Inject() (companiesDao: CompaniesDAO, computersDao: ComputersDAO, val messagesApi: MessagesApi) extends Controller with I18nSupport {
//class UserService @Inject()(usersDAO: UsersDAO) {
//
//  def addUser(user: User): Future[String] = {
//    usersDAO.add(user)
//  }
//
//  def deleteUser(id: Int): Future[Int] = {
//    usersDAO.delete(id)
//  }
//
//  def getUser(id: Int): Future[Option[User]] = {
//    usersDAO.get(id)
//  }
//
//  def listAllUsers: Future[Seq[User]] = {
//    usersDAO.listAll
//  }
//
//  def login(creds: Credentials): Future[Boolean] = {
//    // do some checking of the session here, to see if they are already logged in?
//
//
//    usersDAO.checkCredentials(creds).map {
//      match {
//        case Some(user) => true
//        case None => false
//      }
//    }
//  }
//}