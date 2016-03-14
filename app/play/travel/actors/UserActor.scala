package play.travel.actors

import play.travel.actors.messages._

import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import play.travel.models.{UserTable, User}

import scala.slick.lifted.TableQuery


case class FindEntityByEmail(email: String)

class UserActor extends BaseActor {

  val UserQuery = TableQuery[UserTable]

  override def receive: Receive = {

    case FindEntityByEmail(email: String) =>
      sender ! findByEmail(Option(email))
    case ListEntities() =>
      sender ! list()
    case CreateEntity(user: User) =>
      sender ! create(user)
    case DeleteEntity(id: Int) =>
      sender ! delete(id)
    case ReadEntity(id) =>
      sender ! read(id)
    case UpdateEntity(user: User) =>
      sender ! update(user)
  }

  /**
    * Lists all entities
    * @return
    */
  def findByEmail(email: Option[String]): Option[User] = {
    email match {
      case Some(s) => DB.withSession { implicit session: Session =>
          UserQuery.filter(_.email === email).firstOption
        }
      case None => None
    }
  }

  /**
   * Lists all entities
   * @return
   */
  def list(): List[User] = {
    DB.withSession { implicit session: Session =>
      UserQuery.list
    }
  }

  /**
   * Deletes entity
   * @param id
   * @return number of affected rows
   */
  def delete(id: Int): Int = {
    DB.withSession { implicit session: Session =>
      UserQuery.filter(_.id === id).delete
    }
  }

  /**
   * Returns entity if it exists
   * @param id
   * @return entity
   */
  def read(id: Int): Option[User] = {
    DB.withSession { implicit session: Session =>
      UserQuery.filter(_.id === id).firstOption
    }
  }

  /**
   * Creates entity
   * @param user
   * @return inserted id
   */
  def create(user: User): Int = {
    DB.withSession { implicit session: Session =>
        (UserQuery returning UserQuery.map(_.id)) += user
    }
  }

  /**
   * Updates entity
   * @param user
   * @return number of affected rows
   */
  def update(user: User): Int = {
    DB.withSession { implicit session: Session =>
      UserQuery.filter(_.id === user.id.get).update(user)
    }
  }

}
