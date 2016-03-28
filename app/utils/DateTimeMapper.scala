//package utils
//
//import slick._
//import java.sql.Date
//import org.joda.time.DateTime
//
//
//object DateTimeMapper {
//
//  implicit def date2dateTime = MappedColumnType.base[DateTime, Date] (
//    dateTime => new Date(dateTime.getMillis),
//    date => new DateTime(date)
//  )
//
//}