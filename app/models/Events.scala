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

case class Events( _id: Option[BSONObjectID],
                   name: String,
                   eventSchedule: List[Event])
