package modules

import com.google.inject.AbstractModule
import models.daos._
import models.services._
import net.codingwell.scalaguice.ScalaModule

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.

  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class AppModule extends AbstractModule with ScalaModule {

  override def configure() = {
    bind[AirlineDAO].to[AirlineDAOImpl]
    bind[AirportDAO].to[AirportDAOImpl]
    bind[FlightDAO].to[FlightDAOImpl]
    bind[ScheduledFlightDAO].to[ScheduledFlightDAOImpl]
    bind[AirlineService].to[AirlineServiceImpl]
    bind[AirportService].to[AirportServiceImpl]
    bind[FlightService].to[FlightServiceImpl]
    bind[ScheduledFlightService].to[ScheduledFlightServiceImpl]
  }

}
