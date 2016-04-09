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
    } yield (a.cityCode, a.cityName, a.countryName)

    // @TODO figure out how to get groupBy working...
    // the docs aren't very helpful... http://slick.typesafe.com/doc/3.0.3/sql-to-slick.html#group-by
    // have submitted a question here: http://stackoverflow.com/questions/36520451/scala-slick-groupby-without-aggregation

//    val q2 = q.groupBy(a => a._1).map{
//      case (cityCode, group) => (cityCode, group.map(_._1))
//    }

//    val sql = action.statements.head
//    println(sql)

    db.run(q.result)
  }
}
