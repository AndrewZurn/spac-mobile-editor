package controllers

import javax.inject.{Inject, Singleton}

import models._
import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents, MongoController}
import play.modules.reactivemongo.json.BSONFormats._
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._
import reactivemongo.bson._
import play.modules.reactivemongo.json._
import models.Implicits._

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.Future

/**
 * Created by Andrew on 11/28/14.
 */
@Singleton
class EventsController @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ScheduleController])

  def find() = Action.async {
    val collection = getDatabase()

    logger.info(s"Searching for events schedule")
    val futureEventSchedule: Future[Option[Events]] = collection
      .find(Json.obj("name" -> "events"))
      .one[Events]

    futureEventSchedule.map {
      resultSchedule => Ok(Json.toJson(resultSchedule.get))
    }
  }

  def create() = Action.async(parse.json) { request =>
    val collection = getDatabase()

    request.body.validate[Events].map {
      events => collection.insert(events).map {
        error => logger.debug(s"Successfully inserted $events with error: $error")
          Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: String) = Action.async(parse.json) { request =>
    val collection = getDatabase()

    val byId = Json.obj("_id" -> Json.obj("$oid" -> id))
    request.body.validate[Events].map {
      events => collection.update(byId, events).map {
        error => logger.debug(s"Successfully updated $events with error: $error")
          Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def getDatabase(): JSONCollection = {
    db.collection[JSONCollection]("events_schedule")
  }

}
