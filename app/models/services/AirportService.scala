package models.services

import models.Airport

import scala.concurrent.Future

/**
  * Handles actions to airports.
  */
trait AirportService {

  /**
    * List all airlines
    *
    * @return
    */
  def list: Future[Seq[Airport]]

  /**
    * List all airlines for select
    *
    * @return
    */
  def listForSelect: Future[Seq[(String, String)]]
}
