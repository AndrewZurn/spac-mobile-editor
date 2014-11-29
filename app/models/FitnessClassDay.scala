package models

/**
 * Created by Andrew on 11/28/14.
 * Holds a list of classes for a given day in the week
 */
case class FitnessClassDay( day: String,
                            classes: List[FitnessClass])

object FitnessClassDayJsonFormat {
  import play.api.libs.json.Json

  implicit val classForm = Json.format[FitnessClassDay]
}