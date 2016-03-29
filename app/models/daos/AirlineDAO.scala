package models.daos

import models.Airline
import scala.concurrent.Future

/**
  * Give access to airline object.
  */
trait AirlineDAO {
  def list: Future[Seq[Airline]]
}
