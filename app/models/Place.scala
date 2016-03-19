package models

/**
  * Created by rossanthony on 15/03/2016.
  */

case class Location(lat: Double, long: Double)

case class Place(name: String, location: Location)

object Place {

  var list: List[Place] = {
    List(
      Place(
        "Sandleford",
        Location(51.377797, -1.318965)
      ),
      Place(
        "Watership Down",
        Location(51.235685, -1.309197)
      )
    )
  }

  def save(place: Place) = {
    list = list ::: List(place)
  }
}
