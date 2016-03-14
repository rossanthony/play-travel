package play.travel.actors.messages



  case class ListEntities()

  case class CreateEntity[T](entity: T)

  case class UpdateEntity[T](entity: T)

  case class DeleteEntity(id: Int)

  case class ReadEntity(id: Int)
