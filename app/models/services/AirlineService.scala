package models.services

import models.Airline
import scala.concurrent.Future

/**
  * Handles actions to airlines.
  */
trait AirlineService {

  /**
    * List all airlines
    *
    * @return
    */
  def list: Future[Seq[Airline]]
}
