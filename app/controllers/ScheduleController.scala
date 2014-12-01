package controllers

import javax.inject.Singleton

import models._
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.BSONFormats._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson._

import scala.concurrent.Future

/**
 * Created by Andrew on 11/28/14.
 */
@Singleton
class ScheduleController extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ScheduleController])

  def find(classType: String) = Action.async {
    val collection: JSONCollection = getFitnessDatabase(classType)

    val futureSchedule: Future[Option[FitnessClassWeek]] = collection
      .find(Json.obj("name" -> classType))
      .one[FitnessClassWeek]

    futureSchedule.map {
      resultSchedule => Ok(Json.toJson(resultSchedule.get))
    }
  }

  def create(classType: String) = Action.async(parse.json) { request =>
    val collection: JSONCollection = getFitnessDatabase(classType)

    request.body.validate[FitnessClassWeek].map {
      schedule => collection.insert(schedule).map {
        error => logger.debug(s"Successfully inserted $schedule.name with error: $error")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: String, classType: String) = Action.async(parse.json) { request =>
    val collection: JSONCollection = getFitnessDatabase(classType)

    val byId = Json.obj("_id" -> Json.obj("$oid" ->id))
    request.body.validate[FitnessClassWeek].map {
      schedule => collection.update(byId, schedule).map {
        error => logger.debug(s"Successfully inserted $schedule.name with error: $error")
          Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def getFitnessDatabase(classType: String): JSONCollection = {
    if (classType == "group") {
      db.collection[JSONCollection]("group_fitness")
    } else if (classType == "small_group") {
      db.collection[JSONCollection]("small_group_fitness")
    } else if (classType == "pilates") {
      db.collection[JSONCollection]("pilates_fitness")
    } else {
      throw new IllegalArgumentException("Illegal Class Type Argument, legal values are group, small_group, or pilates")
    }
  }

}
