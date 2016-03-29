package models.daos

import models.Airport

import scala.concurrent.Future

/**
  * Give access to airline object.
  */
trait AirportDAO {
  def list: Future[Seq[Airport]]

  def listForSelect: Future[Seq[(String, String)]]
}
