package models

import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by Andrew on 11/28/14
 * Holds a list of classes for a given day in the week
 */
case class FitnessClassDay( day: String,
                            classes: List[FitnessClass])

object FitnessClassDay {
  import play.api.libs.json.Json

  implicit val classDayFormat = Json.format[FitnessClassDay]
  implicit val classDayReads = Json.reads[FitnessClassDay]
  implicit val classDayWrites = Json.writes[FitnessClassDay]
}
