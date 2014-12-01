package models

import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by Andrew on 11/28/14.
 * Class that holds a list of each FitnessClassDay, which
 * represents a week-by-week fitess schedule.
 */
case class FitnessClassWeek( _id: Option[BSONObjectID],
                             name:String,
                             classSchedule: List[FitnessClassDay])

object FitnessClassWeek {
  import play.api.libs.json.Json

  implicit val classWeekFormat = Json.format[FitnessClassWeek]
  implicit val classWeekReads = Json.reads[FitnessClassWeek]
  implicit val classWeekWrites = Json.writes[FitnessClassWeek]
}