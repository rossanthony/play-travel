package play.travel.actors

import play.travel.actors.messages._

import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

import play.travel.models.{FlightTable, Flight}

import scala.slick.lifted.TableQuery


class FlightActor extends BaseActor {

  val FlightQuery = TableQuery[FlightTable]

  override def receive: Receive = {
    case ListEntities() =>
      sender ! list()
    case CreateEntity(flight: Flight) =>
      sender ! create(flight)
    case DeleteEntity(id: Int) =>
      sender ! delete(id)
    case ReadEntity(id) =>
      sender ! read(id)
    case UpdateEntity(flight: Flight) =>
      sender ! update(flight)
  }

  /**
   * Lists all entities
   * @return
   */
  def list(): List[Flight] = {
    DB.withSession { implicit session: Session =>
      FlightQuery.list
    }
  }

  /**
   * Deletes entity
   * @param id
   * @return number of affected rows
   */
  def delete(id: Int): Int = {
    DB.withSession { implicit session: Session =>
      FlightQuery.filter(_.id === id).delete
    }
  }

  /**
   * Returns entity if it exists
   * @param id
   * @return entity
   */
  def read(id: Int): Option[Flight] = {
    DB.withSession { implicit session: Session =>
      FlightQuery.filter(_.id === id).firstOption
    }
  }

  /**
   * Creates entity
   * @param flight
   * @return inserted id
   */
  def create(flight: Flight): Int = {
    DB.withSession { implicit session: Session =>
        (FlightQuery returning FlightQuery.map(_.id)) += flight
    }
  }

  /**
   * Updates entity
   * @param flight
   * @return number of affected rows
   */
  def update(flight: Flight): Int = {
    DB.withSession { implicit session: Session =>
      FlightQuery.filter(_.id === flight.id.get).update(flight)
    }
  }

}
