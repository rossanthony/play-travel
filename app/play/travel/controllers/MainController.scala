package play.travel.controllers

import play.api.mvc.{Action, Controller}

object MainController extends Controller {

//  def index = Action {
//    Ok(views.html.index.render("Welcome to Scala+Play REST service base. As you can see, your awesome backend has also front-end powers!"))
//  }

  def index = Action {
    Ok(views.html.index())
  }

}
