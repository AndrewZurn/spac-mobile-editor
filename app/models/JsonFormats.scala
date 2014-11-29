package models

import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by Andrew on 11/29/14.
 */
object JsonFormats {
  import play.api.libs.json.Json

  implicit val classFormat = Json.format[FitnessClass]
  implicit val classDayFormat = Json.format[FitnessClassDay]
  implicit val classWeekFormat = Json.format[FitnessClassWeek]
}
