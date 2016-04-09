package models.services

import javax.inject.Inject

import models.Airport
import models.daos.AirportDAO

import scala.concurrent.Future

/**
  * Handles actions to airports.
  *
  * @param airportDAO The airport DAO implementation.
  */
class AirportServiceImpl @Inject()(airportDAO: AirportDAO) extends AirportService {

  /**
    * Retrieves a list of airports.
    *
    * @return a list of all airports.
    */
  def list: Future[Seq[Airport]] = airportDAO.list

  def listForSelect: Future[Seq[(String, String, String)]] = airportDAO.listForSelect
}
