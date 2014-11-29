package models

import reactivemongo.bson.BSONObjectID

/**
 * Created by Andrew on 11/28/14.
 * Class that holds a list of each FitnessClassDay, which
 * represents a week-by-week fitess schedule.
 */
case class FitnessClassWeek( _id: BSONObjectID,
                             name:String,
                             classSchedule: List[FitnessClassDay])

object FitnessClassWeekJsonFormat {
  import play.api.libs.json.Json

  implicit val classFormat = Json.format[FitnessClassWeek]
}