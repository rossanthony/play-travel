package modules

import com.google.inject.AbstractModule
import models.daos.{FlightDAOImpl, FlightDAO}
import models.services.{FlightServiceImpl, FlightService}
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
    bind[FlightDAO].to[FlightDAOImpl]
    bind[FlightService].to[FlightServiceImpl]
  }

}
