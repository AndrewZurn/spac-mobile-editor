package models

import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by Andrew on 11/28/14.
 */
case class FitnessClass( name: String,
                  time: String,
                  instructor: String,
                  room: String)

object FitnessClass {
  import play.api.libs.json.Json

  implicit val classFormat = Json.format[FitnessClass]
  implicit val classReads = Json.reads[FitnessClass]
  implicit val classWrites = Json.writes[FitnessClass]
}