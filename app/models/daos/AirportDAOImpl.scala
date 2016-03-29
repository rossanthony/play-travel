package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

/**
  * Give access to the airports object using Slick
  */
class AirportDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends AirportDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickAirports.filter(_.id === id))

  def list = db.run(slickAirports.result)

  def listForSelect = {
    val q = for {
      a <- slickAirports
    } yield (a.name, a.cityName)

    val action = q.result
    db.run(action)
  }
}
