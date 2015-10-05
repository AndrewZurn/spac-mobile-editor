package models

/**
 * Created on 10/5/15
 * @author Andrew Zurn
 */
object Implicits {
  import play.api.libs.json.Json
  import play.modules.reactivemongo.json._

  lazy implicit val eventFormat = Json.format[Event]
  lazy implicit val eventReads = Json.reads[Event]
  lazy implicit val eventWrites = Json.writes[Event]

  lazy implicit val eventsFormat = Json.format[Events]
  lazy implicit val eventsReads = Json.reads[Events]
  lazy implicit val eventsWrites = Json.writes[Events]

  lazy implicit val classFormat = Json.format[FitnessClass]
  lazy implicit val classReads = Json.reads[FitnessClass]
  lazy implicit val classWrites = Json.writes[FitnessClass]

  lazy implicit val classDayFormat = Json.format[FitnessClassDay]
  lazy implicit val classDayReads = Json.reads[FitnessClassDay]
  lazy implicit val classDayWrites = Json.writes[FitnessClassDay]

  lazy implicit val classWeekFormat = Json.format[FitnessClassWeek]
  lazy implicit val classWeekReads = Json.reads[FitnessClassWeek]
  lazy implicit val classWeekWrites = Json.writes[FitnessClassWeek]
}
