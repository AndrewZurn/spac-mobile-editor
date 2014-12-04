package models

import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by azurn on 12/4/14.
 */
case class Event( name: String,
                  date: String,
                  location: String,
                  description: String,
                  contact: String)

object Event {
  import play.api.libs.json.Json

  implicit val eventFormat = Json.format[Event]
  implicit val eventReads = Json.reads[Event]
  implicit val eventWrites = Json.writes[Event]
}

case class Events( _id: Option[BSONObjectID],
                   name: String,
                   eventSchedule: List[Event])

object Events {
  import play.api.libs.json.Json

  implicit val eventsFormat = Json.format[Events]
  implicit val eventsReads = Json.reads[Events]
  implicit val eventsWrites = Json.writes[Events]
}
