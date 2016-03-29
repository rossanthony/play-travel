package models.daos

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

/**
  * Give access to the airline object using Slick
  */
class AirlineDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends AirlineDAO with DAOSlick {

  import driver.api._

  private val queryById = Compiled(
    (id: Rep[Int]) => slickAirlines.filter(_.id === id))

  def list = db.run(slickAirlines.result)
}
