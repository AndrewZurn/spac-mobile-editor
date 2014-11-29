package models

/**
 * Created by Andrew on 11/28/14.
 */
case class FitnessClass( name: String,
                  time: String,
                  instructor: String,
                  room: String)

object FitnessClassJsonFormats {
  import play.api.libs.json.Json

  implicit val classFormat = Json.format[FitnessClass]
}