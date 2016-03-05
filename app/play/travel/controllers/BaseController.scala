package play.travel.controllers

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import play.api.mvc.Controller

/**
 * Base class for all controllers of the application
 */
abstract class BaseController extends Controller {
  implicit val TIMEOUT = Timeout(5, TimeUnit.SECONDS) //sample common timeout
}
