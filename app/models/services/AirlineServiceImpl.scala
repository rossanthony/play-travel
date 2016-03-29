package models.services

import javax.inject.Inject
import models.Airline
import models.daos.AirlineDAO
import scala.concurrent.Future

/**
  * Handles actions to airlines.
  *
  * @param airlineDAO The airline DAO implementation.
  */
class AirlineServiceImpl @Inject() (airlineDAO: AirlineDAO) extends AirlineService {

  /**
    * Retrieves a list of airlines.
    *
    * @return a list of all airlines.
    */
  def list: Future[Seq[Airline]] = airlineDAO.list
}
